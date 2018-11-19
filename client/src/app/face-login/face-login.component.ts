import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {WebcamImage} from "../webcam/domain/webcam-images";
import {WebcamComponent} from "../webcam/webcam.component";
import {AuthenticationService} from "../service/authentication.service";
import {FirebaseService} from "../service/firebase.service";
import {reserveSlots} from "@angular/core/src/render3/instructions";
import {formatDate} from "@angular/common";
import {Router} from "@angular/router";

@Component({
  selector: 'app-face-login',
  templateUrl: './face-login.component.html',
  styleUrls: ['./face-login.component.css']
})

export class FaceLoginComponent implements OnInit {

  @ViewChild(WebcamComponent) webCam;

  today = new Date();

  constructor(private firebaseService: FirebaseService, private authenticationService: AuthenticationService, private router: Router) {
  }

  ngOnInit() {
  }

  login() {
    let imageUrl: string;
    let fullPath = 'faceLogin/' + this.webCam.deviceId + formatDate(this.today, 'ddMMyyhhmm', 'en-US', '+0700')
    this.firebaseService.saveImageToStorage(this.webCam.webcamImage.imageAsDataUrl, fullPath)
      .then(res => {
        console.log(res)
        this.firebaseService.getImageUrl(fullPath)
          .then(response => {
            console.log(response)
            imageUrl = response;
            this.authenticationService.loginWithFace(imageUrl)
              .subscribe( data => {
                console.log(data);
              });
          });
      });
    // โยงหน้าเว็ปเฉยๆยังไม่ได้เปรียบเทียบหน้า รอเปรียบเทียบหน้าเสร็จเอ่อันนี้ไปต่อเพื่อลิงค์หน้าเว็ปได้เลย

    let successRate: number;

    successRate = 50;

    if (successRate >= 80) {
      this.router.navigate(['/faceLoginSuccess'])
    } else {
      if (window.confirm("Face login fail \nDo you want to login by email?")) {
        this.router.navigate(['/emailLogin']);
      }
    }

  }

}
