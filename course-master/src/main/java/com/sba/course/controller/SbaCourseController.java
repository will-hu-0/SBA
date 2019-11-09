package com.sba.course.controller;

import com.sba.course.mapper.CourseMapper;
import com.sba.course.model.CourseMentor;
import com.sba.course.model.RestResponse;
import com.sba.course.repository.CourseRepository;
import com.sba.course.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sba.course.entity.Course;
import com.sba.course.entity.Rate;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SbaCourseController {

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private RateRepository rateRepository;

  @Autowired
  private CourseMapper courseMapper;

  @RequestMapping(value = "/addcourse", method = RequestMethod.POST, produces = "application/json")
  public ResponseEntity<RestResponse> addCourse(@RequestBody Course course) {
    try {
      courseRepository.save(course);
      RestResponse rsp = new RestResponse(200, "Course Created");
      return new ResponseEntity<>(rsp, HttpStatus.CREATED);
    } catch (Exception ex) {
      RestResponse rsp = new RestResponse(500, ex.getMessage());
      return new ResponseEntity<>(rsp, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(value = "/addrate", method = RequestMethod.POST, produces = "application/json")
  public ResponseEntity<RestResponse> addRate(@RequestBody Rate rate) {
    try {
      rateRepository.save(rate);
      RestResponse rsp = new RestResponse(200, "Rated");
      return new ResponseEntity<>(rsp, HttpStatus.CREATED);
    } catch (Exception ex) {
      RestResponse rsp = new RestResponse(500, ex.getMessage());
      return new ResponseEntity<>(rsp, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(value = "/listcourse", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<RestResponse> finUserCourses(
          @RequestParam String mentorname,
          @RequestParam Integer progress) {

    try {

      List<CourseMentor> coursementors = courseMapper.findMentorCourse(mentorname, progress);
      if (coursementors.size() > 0) {
        RestResponse rsp = new RestResponse(200, "Found Courses", coursementors);
        return new ResponseEntity<>(rsp, HttpStatus.OK);
      } else {
        RestResponse rsp = new RestResponse(404, "No Found Courses");
        return new ResponseEntity<>(rsp, HttpStatus.OK);
      }
    } catch (Exception ex) {
      RestResponse rsp = new RestResponse(500, ex.getMessage());
      return new ResponseEntity<>(rsp, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(value = "/listavailablecourse", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<RestResponse> finMentorAvailableCourses(
          @RequestParam String mentorname) {
    try {
      List<CourseMentor> coursementors = courseMapper.findMentprAvailableCourse(mentorname);
      if (coursementors.size() > 0) {
        RestResponse rsp = new RestResponse(200, "Found Courses", coursementors);
        return new ResponseEntity<>(rsp, HttpStatus.OK);
      } else {
        RestResponse rsp = new RestResponse(404, "No Found Courses");
        return new ResponseEntity<>(rsp, HttpStatus.OK);
      }
    } catch (Exception ex) {
      RestResponse rsp = new RestResponse(500, ex.getMessage());
      return new ResponseEntity<>(rsp, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
