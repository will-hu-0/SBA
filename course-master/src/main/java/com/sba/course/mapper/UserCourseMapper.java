package com.sba.course.mapper;

import java.util.List;

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

}
