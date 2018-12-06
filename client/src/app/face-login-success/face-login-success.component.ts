import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {FaceLoginComponent} from '../face-login/face-login.component';

@Component({
  selector: 'app-face-login-success',
  templateUrl: './face-login-success.component.html',
  styleUrls: ['./face-login-success.component.css']
})
export class FaceLoginSuccessComponent implements OnInit {

  @ViewChild(FaceLoginComponent) faceLoginComponent: FaceLoginComponent;

  showConfidence: string;
  showStudentId: string

  constructor() {
  }

  ngOnInit() { }


}
