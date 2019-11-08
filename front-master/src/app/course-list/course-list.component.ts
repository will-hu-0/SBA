import { Component, OnInit, Input } from '@angular/core';
import { CourseService } from '../service/course.service';
import { AlertService } from '../service/alert.service';
import { MentorCourse } from '../models/mentorCourse';

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.css']
})
export class CourseListComponent implements OnInit {

  mentorname: string;
  showMentorCourse: boolean;
  showAvailableMentorCourse: boolean;
  showCompletedMentorCourse: boolean;
  mentorcourses: MentorCourse[];
  mentoravailablecourses: MentorCourse[];
  mentorcompletedcourses: MentorCourse[];
  @Input() searchText: string;

  constructor(private courseservice: CourseService,
              private alertService: AlertService) { }

  ngOnInit() {
    this.mentorname = JSON.parse(localStorage.getItem('currentUser')).username;
    this.getMentorCourse();
  }

  getMentorCourse() {
    this.showMentorCourse = true;

    this.courseservice.findMentorCourses(1, this.mentorname).subscribe(data => {

        // tslint:disable-next-line:no-string-literal
        if (data['code'] === 200) {
        // tslint:disable-next-line:no-string-literal
        this.mentorcourses = data['data'];
        this.showMentorCourse = false;
      // tslint:disable-next-line:no-string-literal
      } else if (data['code'] === 404) {
        // tslint:disable-next-line:no-string-literal
        this.showMentorCourse = false;
        // tslint:disable-next-line:no-string-literal
        this.alertService.warn(data['message']);
      }

    },
    error => {
      this.alertService.error(error);
      this.showMentorCourse = false;
          });
    }

    getCoursesMentor() {
      this.showAvailableMentorCourse = true;
      this.courseservice.findMentorAvailableCourses(this.mentorname).subscribe(data => {
          // tslint:disable-next-line:no-string-literal
          if (data['code'] === 200) {
          // tslint:disable-next-line:no-string-literal
          this.mentoravailablecourses = data['data'];
          this.showAvailableMentorCourse = false;
        // tslint:disable-next-line:no-string-literal
        } else if (data['code'] === 404) {
          // tslint:disable-next-line:no-string-literal
          this.showAvailableMentorCourse = false;
          // tslint:disable-next-line:no-string-literal
          this.alertService.warn(data['message']);
        }
      },
      error => {
        this.alertService.error(error);
        this.showAvailableMentorCourse = false;
            });
      }

      getCompletedCoursesMentor() {
        this.showCompletedMentorCourse = true;
        this.courseservice.findMentorCompletedCourses(this.mentorname).subscribe(data => {
            // tslint:disable-next-line:no-string-literal
            if (data['code'] === 200) {
            // tslint:disable-next-line:no-string-literal
            this.mentorcompletedcourses = data['data'];
            this.showCompletedMentorCourse = false;
          // tslint:disable-next-line:no-string-literal
          } else if (data['code'] === 404) {
            // tslint:disable-next-line:no-string-literal
            this.showCompletedMentorCourse = false;
            // tslint:disable-next-line:no-string-literal
            this.alertService.warn(data['message']);
          }
        },
        error => {
          this.alertService.error(error);
          this.showCompletedMentorCourse = false;
              });
        }

  selectCourseClick(tab) {

    if (tab.index === 1) {
      this.getCompletedCoursesMentor();

    } else if (tab.index === 2) {
      this.getCoursesMentor();
    } else {
      this.getMentorCourse();
    }

  }

  getStatusColor(status: string) {
    switch (status) {
      case 'expired':
        return 'gray';
      case 'available':
        return 'chartreuse';
      case 'booked':
        return 'lightblue';
      case 'progress':
        return 'bisque';
      case 'completed':
        return 'blueviolet';
    }
}
}
