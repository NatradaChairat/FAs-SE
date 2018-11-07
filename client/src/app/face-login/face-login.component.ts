import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {WebcamImage} from "../webcam/domain/webcam-images";
import {WebcamComponent} from "../webcam/webcam.component";
import {AuthenticationService} from "../service/authentication.service";
import {FirebaseService} from "../service/firebase.service";

@Component({
  selector: 'app-face-login',
  templateUrl: './face-login.component.html',
  styleUrls: ['./face-login.component.css']
})

export class FaceLoginComponent implements OnInit {

  @ViewChild(WebcamComponent) webCam;

  constructor(private firebaseService: FirebaseService, private authenticationService: AuthenticationService) { }

  ngOnInit() {
  }

  login(){
    console.log(this.webCam)
    this.firebaseService.saveImageToStorage(this.webCam.webcamImage.imageAsDataUrl," ")
    //this.authenticationService.loginWithFace(this.webCam)
  }

}
