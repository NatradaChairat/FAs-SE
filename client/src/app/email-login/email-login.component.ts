import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../service/authentication.service";
import {Account} from '../entity/Account';

@Component({
  selector: 'app-email-login',
  templateUrl: './email-login.component.html',
  styleUrls: ['./email-login.component.css']
})
export class EmailLoginComponent implements OnInit {

  email: string
  password: string
  constructor(private authenticationService: AuthenticationService) { }

  ngOnInit() {
  }

  tryLogin(email: string, password: string){
    console.log("tryLogin")
    this.authenticationService.login(email, password)
      .then(res => {
        console.log(res);

      }, err => {
        console.log(err);
      })
  }

}
