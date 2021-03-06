import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../service/authentication.service";
import {Router} from "@angular/router";
import {IntermediaryService} from "../service/intermediary.service";
import {AccountDataServerService} from "../service/account-data-server.service";

@Component({
  selector: 'app-email-login',
  templateUrl: './email-login.component.html',
  styleUrls: ['./email-login.component.css']
})
export class EmailLoginComponent implements OnInit {

  email: string
  password: string

  constructor(private router: Router,
              private intermediaryService: IntermediaryService,
              private authenticationService: AuthenticationService,
              private accountDataServerService: AccountDataServerService) {
  }

  ngOnInit() {
  }

  login(email: string, password: string) {
    this.authenticationService.login(email, password)
      .then(res => {
        const userRes = res.user;
        console.log(userRes);
        const uid = userRes.uid;
        this.accountDataServerService.checkIsStaff(uid)
          .subscribe(isStaff => {
            console.log(isStaff);
            if (isStaff) {
              this.router.navigate(['/staffDashboard']);
            } else {
              const phoneNumber = userRes.phoneNumber;
              console.log(phoneNumber);
              this.intermediaryService.setUid(uid);
              this.intermediaryService.setPhoneNumber(phoneNumber);
              this.router.navigate(['/oneTimePassword']);
            }
          });

      }, err => {
        console.log(err);
        if (window.confirm('Email or password is invalid')) {
          this.password = '';
        }
      });
  }

}
