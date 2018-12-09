import {Component, OnInit, ViewChild, ɵConsole} from '@angular/core';
import {IntermediaryService} from "../service/intermediary.service";
import {WebcamComponent} from "../webcam/webcam.component";
import {formatDate} from "@angular/common";
import {FirebaseService} from "../service/firebase.service";
import {FaceRecognitionService} from "../service/face-recognition.service";
import {AccountDataServerService} from "../service/account-data-server.service";
import {Account} from "../model/Account.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-train-image',
  templateUrl: './train-image.component.html',
  styleUrls: ['./train-image.component.css']
})
export class TrainImageComponent implements OnInit {

  @ViewChild(WebcamComponent) webCam;

  today = new Date();
  account: any = {};

  constructor(private router: Router,
              private intermediaryService: IntermediaryService,
              private firebaseService: FirebaseService,
              private accountDataServerService: AccountDataServerService,
              private faceRecognitionService: FaceRecognitionService) {
  }

  ngOnInit() {

    this.account = new Account();
  }

  train() {
    console.log('train');
    this.account.uid = this.intermediaryService.getUid();
    let imageUrl: string;
    const fullPath = 'faceLogin/' + this.webCam.deviceId + formatDate(this.today, 'ddMMyyhhmm', 'en-US', '+0700')
    this.firebaseService.saveImageToStorage(this.webCam.webcamImage.imageAsDataUrl, fullPath)
      .then(res => {
        console.log(res)
        this.firebaseService.getImageUrl(fullPath)
          .then(response => {
            console.log(response)
            imageUrl = response;
            this.account.images.push(imageUrl);
            this.account.status = 'training';
            this.accountDataServerService.updateStatus(this.account)
              .subscribe(updateResponse => {
                console.log(updateResponse);
                console.log(this.account);
                this.accountDataServerService.uploadImage(this.account)
                  .subscribe(uploadResponse => {
                    console.log(uploadResponse);
                    if (uploadResponse) {
                      this.router.navigate(['/emailLoginSuccess']);
                    }
                  });
              });

            // this.faceRecognitionService.addFaceInLargePersonGroup('', imageUrl)
            //   .subscribe(apiRes => {
            //   });

          });
      });
  }

}
