import {Component, OnInit, ViewChild} from '@angular/core';
import {IntermediaryService} from '../service/intermediary.service';
import {WebcamComponent} from '../webcam/webcam.component';
import {formatDate} from '@angular/common';
import {FirebaseService} from '../service/firebase.service';
import {AccountDataServerService} from '../service/account-data-server.service';
import {Account} from '../model/Account.model';
import {ActivatedRoute, Params, Router} from '@angular/router';

@Component({
  selector: 'app-train-image',
  templateUrl: './train-image.component.html',
  styleUrls: ['./train-image.component.css']
})
export class TrainImageComponent implements OnInit {

  @ViewChild(WebcamComponent) webCam;

  today = new Date();
  account: any = {};
  refParam = '';

  isProcessing = false;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private intermediaryService: IntermediaryService,
              private firebaseService: FirebaseService,
              private accountDataServerService: AccountDataServerService) {
  }

  ngOnInit() {

    this.account = new Account();

    this.route.params.subscribe((param: Params) => {
      this.refParam = param['param'];
    });
  }

  train() {
    this.isProcessing = true;
    console.log('train');
    this.account.uid = this.refParam;
    console.log(this.account);
    let imageUrl: string;
    const fullPath = 'faceTrain/' + this.webCam.deviceId + formatDate(this.today, 'ddMMyyhhmm', 'en-US', '+0700');
    this.firebaseService.saveImageToStorage(this.webCam.webcamImage.imageAsDataUrl, fullPath)
      .then(res => {
        console.log(res);
        this.firebaseService.getImageUrl(fullPath)
          .then(response => {
            console.log(response);
            imageUrl = response;
            this.account.images.push(imageUrl);
            this.accountDataServerService.getAccountByParam(this.refParam)
              .subscribe((getAccountRes: Account) => {
                console.log('GetAccount Res: ');
                console.log(getAccountRes);
                if (getAccountRes.status === 'disapproved') {
                  console.log('isRejectedAccount');
                  this.accountDataServerService.uploadImage(this.account)
                    .subscribe(uploadResponse => {
                      console.log(uploadResponse);
                      if (uploadResponse) {
                        this.router.navigate(['/emailLoginSuccess']);
                      }
                    });
                } else {
                  this.account.status = 'training';
                  this.accountDataServerService.updateStatus(this.account)
                    .subscribe(updateResponse => {
                      console.log(updateResponse);
                      console.log(this.account);
                      this.accountDataServerService.uploadImage(this.account)
                        .subscribe(uploadResponse => {
                          console.log(uploadResponse);
                          if (uploadResponse) {
                            if (window.confirm('Waiting for authentication from staff.')) {
                              this.router.navigate(['/homepage']);
                            }
                          }
                        });
                    });
                }
              });
          });
      });
  }

  skip(){
    this.router.navigate(['/emailLoginSuccess']);
  }

}
