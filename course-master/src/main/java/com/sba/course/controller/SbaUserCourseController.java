package com.sba.course.controller;

import java.util.List;

import com.sba.course.model.MentorCourse;
import com.sba.course.model.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sba.course.mapper.UserCourseMapper;
import com.sba.course.model.UserCourse;

@RestController
@RequestMapping("/api/v1/user")
public class SbaUserCourseController {

  @Autowired
  private UserCourseMapper usercoursemapper;

  @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<RestResponse> finUserCourses(@RequestParam String username,
                                                     @RequestParam Integer progress) {
    try {
      List<UserCourse> usercourses = usercoursemapper.findUserCourse(username, progress);
      if (usercourses.size() > 0) {
        RestResponse rsp = new RestResponse(200, "Found Courses", usercourses);
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

  @RequestMapping(value = "/listdone", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<RestResponse> finUserCompletedCourses(@RequestParam String username) {
    try {
      List<MentorCourse> userCompletedCourses = usercoursemapper.findUserCompletedCourse(username);
      if (userCompletedCourses.size() > 0) {
        RestResponse rsp = new RestResponse(200, "Found Courses", userCompletedCourses);
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
