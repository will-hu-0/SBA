package com.sba.course.mapper;

import java.util.List;

import com.sba.course.model.MentorCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sba.course.model.UserCourse;
import org.springframework.stereotype.Service;

@Service
@Mapper
public interface UserCourseMapper {

	@Select("SELECT id,skill,name,description,start_date as startDate," +
					"end_date as endDate,mentor_name as mentorName,progress, fee " +
					"FROM sba_course.course where progress=#{progress} and user_name=#{username}")
	List<UserCourse> findUserCourse(@Param("username") String username, @Param("progress") Integer progress);

	@Select("SELECT a.id,a.name,a.mentor_name,a.skill,a.start_date as startDate,a.end_date as endDate, " +
			"DATEDIFF(a.end_date, a.start_date) as duration, a.fee, " +
			"coalesce(b.rating, 0) as rate, a.description " +
			"FROM sba_course.course a  " +
			"left join (SELECT course_id as courseId, round(avg(rating)) as rating " +
			"FROM sba_course.rate group by course_id) b on a.id =b.courseId " +
			"where a.status='completed' and a.user_name=#{username}")
	List<MentorCourse> findUserCompletedCourse(@Param("username") String username);
}
