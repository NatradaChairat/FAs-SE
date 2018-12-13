import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {IntermediaryService} from "../service/intermediary.service";
import {Account} from "../model/Account.model";
import {AuthenticationService} from "../service/authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-face-login-success',
  templateUrl: './face-login-success.component.html',
  styleUrls: ['./face-login-success.component.css']
})
export class FaceLoginSuccessComponent implements OnInit {

  showConfidence: number;
  showStudentId: string;

  value: number;
  mode = 'determinate';
  color = 'primary';
  bufferValue = 75;

  constructor(private intermediaryService: IntermediaryService,
              private authenticitionService: AuthenticationService,
              private router: Router) {
  }

  ngOnInit() {
    this.showConfidence = this.intermediaryService.getConfidence();
    this.showStudentId = this.intermediaryService.getStudentId();
    this.value = this.showConfidence * 100;
  }

  logout() {
    this.authenticitionService.logout();
    this.router.navigate(['/homepage']);
  }


}
