import {Component, OnInit, ViewChild} from '@angular/core';
import {IntermediaryService} from "../service/intermediary.service";
import {WebcamComponent} from "../webcam/webcam.component";
import {formatDate} from "@angular/common";
import {FirebaseService} from "../service/firebase.service";

@Component({
  selector: 'app-train-image',
  templateUrl: './train-image.component.html',
  styleUrls: ['./train-image.component.css']
})
export class TrainImageComponent implements OnInit {

  @ViewChild(WebcamComponent) webCam;

  today = new Date();

  constructor(private intermediaryService: IntermediaryService,
              private firebaseService: FirebaseService) {
  }

  ngOnInit() {
  }

  train() {
    console.log('train');

    let imageUrl: string;
    const fullPath = 'faceLogin/' + this.webCam.deviceId + formatDate(this.today, 'ddMMyyhhmm', 'en-US', '+0700')
    this.firebaseService.saveImageToStorage(this.webCam.webcamImage.imageAsDataUrl, fullPath)
      .then(res => {
        console.log(res)
        this.firebaseService.getImageUrl(fullPath)
          .then(response => {
            console.log(response)
            imageUrl = response;

          });
      });
  }

}
