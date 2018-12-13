import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../service/authentication.service";
import {Router} from "@angular/router";
import {IntermediaryService} from "../service/intermediary.service";

@Component({
  selector: 'app-email-login-success',
  templateUrl: './email-login-success.component.html',
  styleUrls: ['./email-login-success.component.css']
})
export class EmailLoginSuccessComponent implements OnInit {

  uid: string;

  constructor(private authenticitionService: AuthenticationService,
              private intermediaryService: IntermediaryService,
              private router: Router) { }

  ngOnInit() {
    this.uid = this.intermediaryService.getUid();
  }

  logout() {
    this.authenticitionService.logout();
    this.router.navigate(['/homepage']);
  }

}
