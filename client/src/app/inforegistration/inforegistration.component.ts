import {Component, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {ActivatedRoute, Params, Router, RouterEvent} from "@angular/router";
import {AccountDataServerService} from "../service/account-data-server.service";
import {MatDialog} from "@angular/material";
import {Account} from "../model/Account.model";
import {DialogComponent} from "../dialog/dialog.component";
import {DatePipe} from "@angular/common";
import {IntermediaryService} from "../service/intermediary.service";


@Component({
  selector: 'app-inforegistration',
  templateUrl: './inforegistration.component.html',
  styleUrls: ['./inforegistration.component.css']
})
export class InforegistrationComponent implements OnInit {
  account: any = {};
  refParam: string;
  infoRegistrerForm: FormGroup;

  type: string;
  title: string;
  detail: string;
  isWarningMessage: boolean;
  isOptionMessage: boolean;

  constructor(private route: ActivatedRoute,
              private formBuilder: FormBuilder,
              private router: Router,
              private accountDataServerService: AccountDataServerService,
              private intermediaryService: IntermediaryService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.account = new Account();
    this.route.params.subscribe((param: Params) => {
      this.refParam = (param['param']);
    })
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '350px',
      disableClose: true,
      data: {
        type: this.type,
        title: this.title,
        detail: this.detail,
        isWarningMessage: this.isWarningMessage,
        isOptionMessage: this.isOptionMessage
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  onSubmit(account: Account) {
    console.log(account.dateofbirth);
    this.checkDuplicatedStudentId(account);

  }

  checkDuplicatedStudentId(account: Account): any {
    this.accountDataServerService.checkDuplicatedStudentId(account.studentId)
      .subscribe(data => {
        if (data) {
          this.type = 'Error';
          this.title = 'Can not submit the form.';
          this.detail = 'StudentId is duplicated.';
          this.isWarningMessage = true;
          this.isOptionMessage = false;
          this.openDialog();
        } else {
          this.checkDuplicatedPhonenumber(account);
        }
      });

  }

  checkDuplicatedPhonenumber(account: Account): any {
    this.accountDataServerService.checkDuplicatedPhonenumber(account.phonenumber)
      .subscribe(data => {
        console.log(data)
        if (data) {
          this.type = 'Error';
          this.title = 'Can not submit the form.';
          this.detail = 'Phone number is duplicated.';
          this.isWarningMessage = true;
          this.isOptionMessage = false;
          this.openDialog();
        } else {
          this.intermediaryService.setAccount(account);
          this.router.navigate(['/videoRegistration/' + this.refParam]);
        }
      });
  }



}
