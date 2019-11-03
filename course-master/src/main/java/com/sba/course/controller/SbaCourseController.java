package com.sba.course.controller;

import com.sba.course.model.RestResponse;
import com.sba.course.repository.CourseRepository;
import com.sba.course.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sba.course.entity.Course;
import com.sba.course.entity.Rate;

@RestController
@RequestMapping("/api/v1")
public class SbaCourseController {

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private RateRepository rateRepository;

  @RequestMapping(value = "/addcourse", method = RequestMethod.POST, produces = "application/json")
  public ResponseEntity<RestResponse> addCourse(@RequestBody Course course) {
    try {
      courseRepository.save(course);
      RestResponse rsp = new RestResponse(200, "Course Created");
      return new ResponseEntity<RestResponse>(rsp, HttpStatus.CREATED);
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

}
