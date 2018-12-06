import {Component, OnInit} from '@angular/core';
import {Account} from "../model/Account.model";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {AccountDataServerService} from "../service/account-data-server.service";
import {MatDialog} from "@angular/material";
import {DialogComponent} from "../dialog/dialog.component";

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

  constructor(private router: Router, private route: ActivatedRoute,
              private accountDataServerService: AccountDataServerService,
              private dialog: MatDialog) { }

  ngOnInit() {
    this.route.params.subscribe((params: Params) => {
      this.refParam = params['key'];
      this.accountDataServerService.getAccountByParam(params['key'])
        .subscribe((res: any) => {
            this.account = res;
            const images = this.account.images;
            this.showImageUrl = images[0];
            console.log(this.showImageUrl);
          }, err => {
            //this.reSendEmail(params['key']);
          }
        );
    });
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '480px',
      disableClose: true,
      data: {type: this.type,
            title: this.title,
            detail: this.detail,
            isWarningMessage: this.isWarningMessage,
            isOptionMessage: this.isOptionMessage}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });

  }

  accept() {
    this.account.status = 'approved';
    this.accountDataServerService.updateStatus(this.account)
      .subscribe((res: any) => {
        if (res) {
          this.accountDataServerService.sendResultAuthenProcessToEmail(this.refParam, 'approved')
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

    this.account.status = 'disapproved';
    this.accountDataServerService.updateStatus(this.account)
      .subscribe((res: any) => {
        if (res) {
          this.accountDataServerService.sendResultAuthenProcessToEmail(this.refParam, 'disapproved')
            .subscribe((result: any) => {
              if (result) {
                this.router.navigate(['/staffDashboard']);
              }
            });
        } else {
          this.reject();
        }
      }, (error: any) => {
        console.log(error);
      });
  }

}
