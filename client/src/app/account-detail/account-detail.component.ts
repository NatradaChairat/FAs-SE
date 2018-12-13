import {Component, OnInit, ViewChild} from '@angular/core';
import {Account} from "../model/Account.model";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {AccountDataServerService} from "../service/account-data-server.service";
import {MatDialog} from "@angular/material";
import {DialogComponent} from "../dialog/dialog.component";
import {FaceRecognitionService} from "../service/face-recognition.service";

@Component({
  selector: 'app-account-detail',
  templateUrl: './account-detail.component.html',
  styleUrls: ['./account-detail.component.css']
})
export class AccountDetailComponent implements OnInit {

  account: Account;
  refParam: string;

  type: string;
  title: string;
  detail: string;
  isWarningMessage: boolean;
  isOptionMessage: boolean;

  showImageUrl: string;

  isDisapproved = false;
  reasonText: string;

  isProcessing = false;

  imageSources;

  isHasOneItem = false;

  oldPersonId: string;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private accountDataServerService: AccountDataServerService,
              private faceRecognitionService: FaceRecognitionService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.route.params.subscribe((params: Params) => {
      this.refParam = params['key'];
      console.log(this.refParam);
      this.accountDataServerService.getAccountByParam(params['key'])
        .subscribe((res: any) => {
            this.account = res;
            const images = this.account.images;
            this.showImageUrl = images[0];
            this.imageSources = images;
            if (this.imageSources.length <= 1) {
              this.isHasOneItem = true;
            }
            console.log(this.account.status);
            if (this.account.status === 'disapproved') {
              this.isDisapproved = true;
              this.accountDataServerService.getReasonByParam(this.refParam)
                .subscribe((response: any) => {
                  const reasonRes: number = response;
                  console.log(reasonRes);
                  if (reasonRes === 1) {
                    this.reasonText = 'Face is blur, not matched with Student card';
                  } else if (reasonRes === 2) {
                    this.reasonText = 'Student ID not matched with Student card';
                  } else if (reasonRes === 3) {
                    this.reasonText = 'No Random text';
                  }
                  console.log(this.reasonText);
                });
            }
          }, err => {
            // this.reSendEmail(params['key']);
          }
        );
    });

  }

  openDialog(): void {
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '480px',
      disableClose: true,
      data: {
        type: this.type,
        title: this.title,
        detail: this.detail,
        isWarningMessage: this.isWarningMessage,
        isOptionMessage: this.isOptionMessage
      }
    });

    dialogRef.afterClosed().subscribe(res => {
      console.log(res);
      const reason = res.reason;
      console.log(reason);
      this.updateRejectStatus(reason);
    });

  }

  accept() {
    this.isProcessing = true;
    this.account.status = 'approved';
    const index = this.account.images.length - 1;
    console.log(this.account);
    this.isContainGroup().then(resolve => {
      console.log(resolve);
      if (resolve) {
        console.log(this.account.images[index]);
        this.faceRecognitionService.detectImage(this.account.images[index])
          .subscribe(r => {
            console.log(r);
          })
        this.faceRecognitionService.addFaceInLargePersonGroup(this.oldPersonId, this.account.images[index])
          .subscribe(persistedFaceId => {
            console.log(persistedFaceId);
          });
      } else {
        this.faceRecognitionService.createPersonInLargePersonGroup(this.account.studentId)
          .subscribe((res: any) => {
            console.log(res);
            const personIdRes = res.personId;
            this.faceRecognitionService.addFaceInLargePersonGroup(personIdRes, this.account.images[index])
              .subscribe(persistedFaceId => {
                console.log(persistedFaceId);
              });
          });
      }
    });

    this.accountDataServerService.updateStatus(this.account)
      .subscribe((res: any) => {
        console.log(res);
        if (res) {
          this.accountDataServerService.sendResultAuthenProcessToEmail(this.refParam, 'approved', '0')
            .subscribe((result: any) => {
              if (result) {
                this.router.navigate(['/staffDashboard']);
              }
            });

        } else {
          this.accept();
        }
      }, (error: any) => {
        console.log(error);
      });

    this.faceRecognitionService.trainLargePersonGroup();
  }

  reject() {
    this.isProcessing = true;
    this.type = 'Select one';
    this.title = 'What are the reason to reject this account?';
    this.detail = 'test';
    this.isWarningMessage = false;
    this.isOptionMessage = true;
    this.openDialog();
  }

  updateRejectStatus(reason: string) {
    this.account.status = 'disapproved';
    this.accountDataServerService.updateStatus(this.account)
      .subscribe((res: any) => {
        if (res) {
          this.accountDataServerService.saveReasonByParam(reason, this.refParam)
            .subscribe((response: any) => {
              console.log(response);
              this.accountDataServerService.sendResultAuthenProcessToEmail(this.refParam, 'disapproved', reason)
                .subscribe((result: any) => {
                  if (result) {
                    this.router.navigate(['/staffDashboard']);
                  }
                });
            });
        } else {
          this.reject();
        }
      }, (error: any) => {
        console.log(error);
      });
  }

  isContainGroup() {
    return new Promise<any>((resolve) => {
      this.faceRecognitionService.getPersonListInLargePersonGroup()
        .subscribe((getLIstResponse: any) => {
          console.log(getLIstResponse);
          for (let i = 0; i <= getLIstResponse.length; i++) {
            const obj = getLIstResponse[i];
            const name = obj.name;
            console.log(name);
            if (name === this.account.studentId) {
              const personId = getLIstResponse[i].personId;
              console.log('LISTINGROUP contain true ' + personId);
              this.oldPersonId = personId;
              resolve(true);
            } else {
              resolve(false);
            }
          }
        });
    });
  }

}
