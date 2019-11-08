import { Component, OnInit, Inject, Input } from '@angular/core';
import {FormBuilder, Validators, FormGroup} from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NewCourse } from '../models/newCourse';
import { CourseService } from '../service/course.service';
import { AlertService } from '../service/alert.service';
import { first } from 'rxjs/operators';
import {MomentDateAdapter, MAT_MOMENT_DATE_ADAPTER_OPTIONS} from '@angular/material-moment-adapter';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material';
import * as _moment from 'moment';
import {DatePipe} from '@angular/common';

export const MY_FORMATS = {
  parse: {
    dateInput: 'LL',
  },
  display: {
    dateInput: 'YYYY-MM-DD',
    monthYearLabel: 'YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'YYYY',
  },
};
@Component({
  selector: 'app-add-course',
  templateUrl: './add-course.component.html',
  styleUrls: ['./add-course.component.css'],
  providers: [
    // `MomentDateAdapter` can be automatically provided by importing `MomentDateModule` in your
    // application's root module. We provide it at the component level here, due to limitations of
    // our example generation script.
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS]},

    {provide: MAT_DATE_FORMATS, useValue: MY_FORMATS},
    {provide: MAT_MOMENT_DATE_ADAPTER_OPTIONS, useValue: {useUtc: true}}
  ]
})
export class AddCourseComponent implements OnInit {

  form: FormGroup;
  loading = false;
  submitted = false;
  username: string;
  minDate: Date;
  constructor(
    private datePipe: DatePipe,
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<AddCourseComponent>,
    @Inject(MAT_DIALOG_DATA) {username},
    private courseService: CourseService,
    private alertService: AlertService) {
      this.username = username;
      this.form = fb.group({
        name: ['', Validators.required],
        description: [''],
        startAt: ['', Validators.required],
        endAt: ['', Validators.required],
        skill: ['', Validators.required],
        fee: ['', Validators.required],
        mentorName: [username]
        });
    }

  ngOnInit() {
    this.minDate = new Date();
  }

  save() {
    if (this.form.valid) {
      const course: NewCourse = {
        name : this.form.value.name,
        description : this.form.value.description,
        skill: this.form.value.skill,
        startDate: this.datePipe.transform(this.form.value.startAt, 'yyyy-MM-dd'),
        endDate: this.datePipe.transform(this.form.value.endAt, 'yyyy-MM-dd'),
        mentorName: this.form.value.mentorName,
        fee: this.form.value.fee
      };
      this.submitted = true;

      // reset alerts on submit
      this.alertService.clear();

      this.loading = true;
      this.courseService.addCourse(course)
      .pipe(first())
        .subscribe(
            data => {
              // tslint:disable-next-line:no-string-literal
              if (data['code'] === 200) {
                // tslint:disable-next-line:no-string-literal
                this.alertService.success(data['message']);
              }
            },
            error => {
                this.alertService.error(error);
                this.loading = false;
            });
      this.dialogRef.close(course);
    }
}

close() {
    this.dialogRef.close();
}

}
