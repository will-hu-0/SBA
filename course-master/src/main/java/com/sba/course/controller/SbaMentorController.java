package com.sba.course.controller;

import java.util.List;

import com.sba.course.model.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sba.course.client.PaymentClient;
import com.sba.course.mapper.MentorMapper;
import com.sba.course.model.MentorCourse;
import com.sba.course.model.Payment;

@RestController
@RequestMapping("/api/v1/mentor")
public class SbaMentorController {

  @Autowired
  private MentorMapper mentorcoursemapper;

  @Autowired
  private PaymentClient paymentclient;

  @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<RestResponse> findCourses() {
    try {
      List<MentorCourse> mentorcourses = mentorcoursemapper.findMentors();
      if (mentorcourses.size() > 0) {

        RestResponse rsp = new RestResponse(200, "Found Courses", mentorcourses);
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

  @RequestMapping(value = "/searchcourse", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<RestResponse> searchCourses() {
    try {
      List<MentorCourse> mentorcourses = mentorcoursemapper.searchMentors();
      if (mentorcourses.size() > 0) {
        RestResponse rsp = new RestResponse(200, "Found Courses", mentorcourses);
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

  @RequestMapping(value = "/book", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<RestResponse> bookCourses(@RequestParam String username,
                                              @RequestParam Integer id,
                                              @RequestParam String mentorname) {
    try {
      mentorcoursemapper.bookCourse(username, id);
      Payment payment = new Payment();
      payment.setCourseId(id);
      payment.setUserName(username);
      payment.setMentorName(mentorname);

      paymentclient.addPayment(payment);

      RestResponse rsp = new RestResponse(200, "Book Sucessful");
      return new ResponseEntity<>(rsp, HttpStatus.OK);
    } catch (Exception ex) {
      RestResponse rsp = new RestResponse(500, ex.getMessage());
      return new ResponseEntity<>(rsp, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
