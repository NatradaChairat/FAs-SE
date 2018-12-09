import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../service/authentication.service";
import {Router} from "@angular/router";
import {IntermediaryService} from "../service/intermediary.service";

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
              private authenticationService: AuthenticationService) {
  }

  ngOnInit() {
  }

  tryLogin(email: string, password: string) {
    this.authenticationService.login(email, password)
      .then(res => {
        console.log(res);
        const userRes = res.user;
        console.log(userRes)
        const phoneNumber = userRes.phoneNumber;
        console.log(phoneNumber);
        const uid = userRes.uid;
        this.intermediaryService.setUid(uid);
        this.intermediaryService.setPhoneNumber(phoneNumber);
        this.router.navigate(['/oneTimePassword']);

      }, err => {
        console.log(err);
        if (window.confirm('Your email or password is incorrect')) {
          this.password = '';
        }
      });
  }

}
