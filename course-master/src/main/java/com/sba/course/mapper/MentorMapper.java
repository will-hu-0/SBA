package com.sba.course.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sba.course.model.MentorCourse;
import org.springframework.stereotype.Service;

@Mapper
@Service
public interface MentorMapper {
	
	@Select("SELECT a.id,a.name,a.mentor_name as mentorName,a.skill,a.start_date as startDate,a.end_date as endDate,a.fee, " +
					"coalesce(b.rating, 0) as rate, a.description FROM sba_course.course a " +
					"left join (SELECT course_id, round(avg(rating)) as rating FROM sba_course.rate group by course_id) b " +
					"on a.id =b.course_id where a.status='available'")
	List<MentorCourse> findMentors();
	
	@Select("SELECT a.id,a.name,a.mentor_name as mentorName,a.skill,a.start_date as startDate,a.end_date as endDate,a.fee," +
					" a.description, a.user_name FROM sba_course.course a where a.user_name is null")
	List<MentorCourse> searchMentors();
	
	@Update("update sba_course.course set user_name=#{username},progress=1 where id = #{id}")
	void bookCourse(@Param("username") String username, @Param("id") Integer id);

}