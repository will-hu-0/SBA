import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NewCourse } from '../models/newCourse';
import { NewRate } from '../models/newRate';
import { environment } from '../../environments/environment';
import { BookCourse } from '../models/bookCourse';

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  constructor(private http: HttpClient) { }

  addCourse(course: NewCourse) {
    return this.http.post(`${environment.gatewayurl}/course/api/v1/addcourse`, course);
  }


  addRate(rate: NewRate) {
    return this.http.post(`${environment.gatewayurl}/course/api/v1/addrate`, rate);
  }

  findCourses() {
    return this.http.get(`${environment.gatewayurl}/course/api/v1/mentor/list`);
  }

  searchCourses() {
    return this.http.get(`${environment.gatewayurl}/course/api/v1/mentor/searchcourse`);
  }


  bookCourses(bookcourse: BookCourse) {
    return this.http.post(`${environment.gatewayurl}/course/api/v1/mentor/book`, bookcourse);
  }

  findUserCourses(progress: number, username: string) {
    return this.http.get(`${environment.gatewayurl}/course/api/v1/user/list?progress=${progress}&username=${username}`);
  }

  findUserCompletedCourses(username: string) {
    return this.http.get(`${environment.gatewayurl}/course/api/v1/user/listdone?&username=${username}`);
  }

  findMentorCourses(progress: number, mentorname: string) {
    return this.http.get(`${environment.gatewayurl}/course/api/v1/listcourse?progress=${progress}&mentorname=${mentorname}`);
  }

  findMentorAvailableCourses(mentorname: string) {
    return this.http.get(`${environment.gatewayurl}/course/api/v1/listavailablecourse?mentorname=${mentorname}`);
  }

  findMentorCompletedCourses(mentorname: string) {
    return this.http.get(`${environment.gatewayurl}/course/api/v1/mentor/listdone?mentorname=${mentorname}`);
  }
}
