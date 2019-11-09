package com.sba.course.mapper;

import com.sba.course.model.CourseMentor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Mapper
public interface CourseMapper {

    @Select("SELECT id,name,skill,start_date as startDate,end_date as endDate,status, " +
            "user_name as userName,description, DATEDIFF(end_date, start_date) as duration, schedule F" +
            "ROM sba_course.course where mentor_name=#{mentorname} and progress = #{progress}")
    List<CourseMentor> findMentorCourse(@Param("mentorname") String mentorname, @Param("progress") Integer progress);


    @Select("SELECT id,name,skill,start_date as startDate,end_date as endDate,status, " +
            "user_name as userName,description, DATEDIFF(end_date, start_date) as duration, schedule " +
            "FROM sba_course.course where mentor_name=#{mentorname} and progress is null")
    List<CourseMentor> findMentprAvailableCourse(@Param("mentorname") String mentorname);
}