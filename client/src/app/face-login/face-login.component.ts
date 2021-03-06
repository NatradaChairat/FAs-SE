import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {WebcamComponent} from '../webcam/webcam.component';
import {AuthenticationService} from '../service/authentication.service';
import {FirebaseService} from '../service/firebase.service';
import {formatDate} from "@angular/common";
import {Router} from "@angular/router";
import {IntermediaryService} from "../service/intermediary.service";
import {AccountDataServerService} from "../service/account-data-server.service";

@Component({
  selector: 'app-face-login',
  templateUrl: './face-login.component.html',
  styleUrls: ['./face-login.component.css']
})

export class FaceLoginComponent implements OnInit {

  @ViewChild(WebcamComponent) webCam;

  today = new Date();

  confidence = 0;
  uid = '';

  isProcessing = false;

  constructor(private firebaseService: FirebaseService,
              private authenticationService: AuthenticationService,
              private accountDataServerService: AccountDataServerService,
              private intermediaryService: IntermediaryService,
              private router: Router) {
  }

  ngOnInit() {
  }

  login() {

    let imageUrl: string;
    const fullPath = 'faceLogin/' + this.webCam.deviceId + formatDate(this.today, 'ddMMyyhhmm', 'en-US', '+0700')
    this.firebaseService.saveImageToStorage(this.webCam.webcamImage.imageAsDataUrl, fullPath)
      .then(res => {
        this.firebaseService.getImageUrl(fullPath)
          .then(response => {
            imageUrl = response;
            this.authenticationService.loginWithFace(imageUrl)
              .then(([nameRes, confidence]) => {
                this.confidence = confidence;
                this.uid = nameRes;
                this.accountDataServerService.checkIsStaff(this.uid)
                  .subscribe(isStaff => {
                    console.log(isStaff);
                    if (isStaff) {
                      this.router.navigate(['/staffDashboard']);
                    }
                  });

                this.intermediaryService.setConfidence(this.confidence);
                this.intermediaryService.setStudentId(this.uid);
                if (this.confidence >= 0.7) {
                  this.router.navigate(['/faceLoginSuccess']);
                } else {
                  if (window.confirm('Face login fail \nDo you want to login by email?')) {
                    this.router.navigate(['/emailLogin']);
                  }
                }
              }, (error: any) => {
                if (window.confirm('Face login fail \nDo you want to login by email?')) {
                  this.router.navigate(['/emailLogin']);
                }
              });
          });
      });

  }

}
