import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Params, Router} from "@angular/router";
import {AccountDataServerService} from "../service/account-data-server.service";
import {InforegistrationComponent} from "../inforegistration/inforegistration.component";
import {FaceRecognitionService} from "../service/face-recognition.service";
import {WebcamComponent} from "../webcam/webcam.component";
import {FirebaseService} from "../service/firebase.service";
import {formatDate} from "@angular/common";
import {Account} from "../model/Account.model";
import {IntermediaryService} from "../service/intermediary.service";


@Component({
  selector: 'app-videoregistration',
  templateUrl: './videoregistration.component.html',
  styleUrls: ['./videoregistration.component.css']
})


export class VideoregistrationComponent implements OnInit, AfterViewInit {

  account: Account;
  randomtext: string;
  refParam: string;

  today = new Date();

  @ViewChild(WebcamComponent) webCamComp;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private firebaseService: FirebaseService,
              private intermediaryService: IntermediaryService) {
  }

  ngOnInit() {
    this.randomtext = this.randomText();
    this.route.params.subscribe((param: Params) => {
      this.refParam = (param['param']);
    })
    console.log(this.refParam);

    this.account = this.intermediaryService.getAccount();

  }

  randomText() {
    let text = '';
    const charset = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    for (let i = 0; i < 6; i++) {
      text += charset.charAt(Math.floor(Math.random() * charset.length));
    }
    return text;
  }

  ngAfterViewInit() {
  }


  onSubmit() {
    // this.faceRecognitionService.createPersonInLargePersonGroup(this.account.studentId)
    //   .subscribe(personId => {
    //     console.log(personId);
    //     this.faceRecognitionService.addFaceInLargePersonGroup()
    //   })
    this.account.randomText = this.randomtext;
    console.log(this.account);
    let imageUrl: string;
    const fullPath = 'faceRegister/' + this.webCamComp.deviceId + formatDate(this.today, 'ddMMyyhhmm', 'en-US', '+0700')
    this.firebaseService.saveImageToStorage(this.webCamComp.webcamImage.imageAsDataUrl, fullPath)
      .then(response => {
        console.log(response);
        this.firebaseService.getImageUrl(fullPath)
          .then(res => {
            console.log(res);
            imageUrl = res;
            this.account.images.push(imageUrl);
            this.intermediaryService.setAccount(this.account);
            this.router.navigate(['phonenoVerification/' + this.refParam]);
          });
      });
  }

}
