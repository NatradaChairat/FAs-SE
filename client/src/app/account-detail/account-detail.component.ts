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

  imageSources;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private accountDataServerService: AccountDataServerService,
              private faceRecognitionService: FaceRecognitionService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.route.params.subscribe((params: Params) => {
      this.refParam = params['key'];
      this.accountDataServerService.getAccountByParam(params['key'])
        .subscribe((res: any) => {
            this.account = res;
            const images = this.account.images;
            this.showImageUrl = images[0];
            this.imageSources = images;
            console.log(this.account.status)
            if (this.account.status === 'disapproved') {
              this.isDisapproved = true;
              this.accountDataServerService.getReasonByParam(this.refParam)
                .subscribe((response: any) => {
                  const reasonRes: number = response;
                  console.log(reasonRes)
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
    this.account.status = 'approved';
    this.faceRecognitionService.createPersonInLargePersonGroup(this.account.studentId)
      .subscribe((res: any) => {
        console.log(res);
        const personIdRes = res.personId;
        const length = this.account.images.length;
        this.faceRecognitionService.addFaceInLargePersonGroup(personIdRes, this.account.images[length - 1])
          .subscribe(persistedFaceId => {
            console.log(persistedFaceId);
            this.faceRecognitionService.trainLargePersonGroup();
          });
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
  }

  reject() {
    this.type = 'Selete one';
    this.title = 'What are the reason to reject this account?'
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

}
