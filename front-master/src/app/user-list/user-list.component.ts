import { Component, OnInit, Input } from '@angular/core';
import { Course } from '../models/course';
import { CourseService } from '../service/course.service';
import { AlertService } from '../service/alert.service';
import { UserCourse } from '../models/userCourse';
import { BookCourse } from '../models/bookCourse';
import { NewRate } from '../models/newRate';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  loading = false;
  rateplus = false;
  rateminus = true;
  votecourse = true;
  courses: Course[];
  userCompletedCourses: Course[];
  userCourses: UserCourse[];
  showCourse: boolean;
  showInProgressCourse: boolean;
  showInCompletedProgressCourse: boolean;
  @Input() userRole: string;
  @Input() searchText: string;
  rating: string;
  username: string;
  rateCount: number;
  rate: NewRate;

  constructor(private courseservice: CourseService,
              private alertService: AlertService) {
                this.rateCount = 0;
               }

  ngOnInit() {
    this.searchCourses();
  }

  searchCourses() {
    this.showCourse = true;
    this.courseservice.searchCourses().subscribe(courses => {
      // tslint:disable-next-line:no-string-literal
      if (courses['code'] === 200) {
        // tslint:disable-next-line:no-string-literal
        this.courses = courses['data'];
        this.showCourse = false;
      // tslint:disable-next-line:no-string-literal
      } else if (courses['code'] === 404) {
        // tslint:disable-next-line:no-string-literal
        this.showCourse = false;
        // tslint:disable-next-line:no-string-literal
        this.alertService.warn(courses['message']);
      }
    },
    error => {
          this.alertService.error(error);
          this.showCourse = false;
          });
  }

  vote(id: number) {
    const rate = {
      courseId: id,
      rating: this.rateCount
    };
    this.votecourse = true;

    this.courseservice.addRate(rate).subscribe(data => {
      this.votecourse = false;
        // tslint:disable-next-line:no-string-literal
      this.alertService.success(data['message']);

    },
    error => {

      this.alertService.error(error);
      this.votecourse = false;
    });
  }

  ratePlus() {
    if (this.rateCount >= 0  && this.rateCount < 5) {
      this.rateminus = false;
      this.votecourse = false;
      this.rateCount++;
      if (this.rateCount === 5) {
        this.rateplus = true;
      }
    } else {
      this.rateplus = true;
    }

  }
  rateMinus() {
    if (this.rateCount > 0 && this.rateCount <= 5) {
      this.rateplus = false;
      this.rateCount--;
      if (this.rateCount === 0) {
        this.rateminus = true;
        this.votecourse = true;
      }
    } else {
      this.rateminus = true;
    }

  }

  book(coursieId: number, mentor: string, sdate: Date, edate: Date, coursefee: number) {

    this.loading = true;
    this.username = JSON.parse(localStorage.getItem('currentUser')).username;
    const bookcourse: BookCourse = {
      courseId: coursieId,
      userName: this.username,
      mentorName: mentor,
      startDate: sdate,
      endDate: edate,
      fee: coursefee
    };
    this.courseservice.bookCourses(bookcourse).subscribe(data => {
      // tslint:disable-next-line:no-string-literal
      if (data['code'] === 200) {
        // tslint:disable-next-line:no-string-literal
        this.alertService.success(data['message']);
        this.loading = false;
        this.searchCourses();
      }
    },
    error => {
      this.alertService.error(error);
      this.loading = false;
      });


  }

  selectCourseClick(tab) {

    this.username = JSON.parse(localStorage.getItem('currentUser')).username;

    if (tab.index === 0) {
      this.searchCourses();
    } else if (tab.index === 1) {
      this.showInProgressCourse = true;
      this.courseservice.findUserCourses(tab.index, this.username).subscribe(data => {
      // tslint:disable-next-line:no-string-literal
      if (data['code'] === 200) {
        // tslint:disable-next-line:no-string-literal
        this.showInProgressCourse = false;
        // tslint:disable-next-line: no-string-literal
        this.userCourses = data['data'];
        // tslint:disable-next-line:no-string-literal
        this.alertService.success(data['message']);
      // tslint:disable-next-line:no-string-literal
      } else if (data['code'] === 404) {
        // tslint:disable-next-line:no-string-literal
        this.showInProgressCourse = false;
        // tslint:disable-next-line:no-string-literal
        this.alertService.warn(data['message']);
      }
    },
    error => {
      this.showInProgressCourse = false;
      this.alertService.error(error);
      });
  } else {
      this.showInCompletedProgressCourse = true;
      this.courseservice.findUserCompletedCourses(this.username).subscribe(data => {
      // tslint:disable-next-line:no-string-literal
      if (data['code'] === 200) {
        // tslint:disable-next-line:no-string-literal
        this.showInCompletedProgressCourse = false;
        // tslint:disable-next-line: no-string-literal
        this.userCompletedCourses = data['data'];
        // tslint:disable-next-line:no-string-literal
        this.alertService.success(data['message']);
      // tslint:disable-next-line:no-string-literal
      } else if (data['code'] === 404) {
        // tslint:disable-next-line:no-string-literal
        this.showInCompletedProgressCourse = false;
        // tslint:disable-next-line:no-string-literal
        this.alertService.warn(data['message']);
      }
    },
    error => {
      this.showInCompletedProgressCourse = false;
      this.alertService.error(error);
      });
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
