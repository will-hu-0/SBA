import { Component, OnInit, Input } from '@angular/core';
import { User } from '../models/user.model';
import { AuthenticationService } from '../service/authentication.service';
import { Router } from '@angular/router';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { AddCourseComponent } from '../add-course/add-course.component';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  currentUser: User;
  searchText: string;

  constructor(private authenticationService: AuthenticationService,
              private router: Router,
              public dialog: MatDialog) {
    this.currentUser = this.authenticationService.currentUserValue;
   }

  ngOnInit() {
  }

  logoutuser() {
    this.authenticationService.logout();
    location.reload(true);
  }

  addCourseDialog() {

    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.width = '500px';

    dialogConfig.data = {
      username : this.currentUser.username
  };

    this.dialog.open(AddCourseComponent,
      dialogConfig);

    // const dialogRef = this.dialog.open(AddCourseComponent,
    //   dialogConfig);

    // dialogRef.afterClosed().subscribe(
    //   val => console.log('Dialog output:', val)
    // );
}

}
