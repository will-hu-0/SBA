import { Component, OnInit, Input } from '@angular/core';
import { CourseService } from '../service/course.service';
import { AlertService } from '../service/alert.service';
import { Course } from '../models/course';

@Component({
  selector: 'app-mentor-list',
  templateUrl: './mentor-list.component.html',
  styleUrls: ['./mentor-list.component.css']
})
export class MentorListComponent implements OnInit {

  courses: Course[];
  showCourse: boolean;
  @Input() userRole: string;
  @Input() searchText: string;

  constructor(private courseservice: CourseService,
              private alertService: AlertService) { }

  ngOnInit() {
    this.getCourses();
  }

  getCourses() {
    this.showCourse = true;
    this.courseservice.findCourses().subscribe(courses => {
      // tslint:disable-next-line:no-string-literal
      if (courses['code'] === 200) {
        // tslint:disable-next-line:no-string-literal
        this.courses = courses['data'];
        this.showCourse = false;
      }
    },
    error => {
          this.alertService.error(error);
          this.showCourse = false;
          });
  }

}
