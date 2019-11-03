import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { AlertService } from '../service/alert.service';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.css']
})
export class AlertComponent implements OnInit, OnDestroy {

  private subscription: Subscription;
    message: any;

  constructor(private alertService: AlertService, private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.subscription = this.alertService.getAlert()
            .subscribe(message => {
                switch (message && message.type) {
                    case 'success':
                        // message.cssClass = 'alert alert-success';
                        this.snackBar.open(message.text, 'close', {
                          duration: 2000,
                        });
                        break;
                    case 'warn':
                          // message.cssClass = 'alert alert-danger';
                          this.snackBar.open(message.text, 'close', {
                            duration: 2000,
                          });
                          break;
                    case 'error':
                        // message.cssClass = 'alert alert-danger';
                        this.snackBar.open(message.text, 'close', {
                          duration: 2000,
                        });
                        break;
                }

                this.message = message;
            });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
}

}
