import { Component, OnInit } from '@angular/core';
import {FormGroup} from "@angular/forms";
import {Account} from "../model/Account.model";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {AccountDataServerService} from "../service/account-data-server.service";
import {MatDialog} from "@angular/material";
import {DialogComponent} from "../dialog/dialog.component";

@Component({
  selector: 'app-phoneno-verification',
  templateUrl: './phoneno-verification.component.html',
  styleUrls: ['./phoneno-verification.component.css']
})
export class PhonenoVerificationComponent implements OnInit {
  otpForm: FormGroup;
  timeout : boolean;
  account: any = {};
  otp: string;
  refCode: string;
  refParam: string;
  model: string;
  type: string;
  title: string;
  detail: string;

  constructor(private route:ActivatedRoute, private router: Router,  private accountDataServerService: AccountDataServerService,  private dialog: MatDialog) { }

  ngOnInit() {
    this.account = new Account();
    this.timeout = false;
    this.model = "";
    this.route.params.subscribe((param: Params) =>{
      this.refParam = (param['param']);
    })
    //5min
    setTimeout(()=>{this.timeout = true},300000);

    this.accountDataServerService.getVerifyPhonenumberCode(this.refParam)
      .subscribe((data: any)=>{
        this.account.phonenumber = data.body.phonenumber.toString();
        this.refCode = data.body.refCode.toString();
        this.otp = data.body.otp.toString();

      }
    );
  }

  openDialog():void{
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '350px',
      data: {type: this.type,title:this.title, detail: this.detail}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  onSubmit(otp: string){
    console.log("show isTimeout "+ this.timeout);
    if(this.otp == otp && !this.timeout){
      this.accountDataServerService.updateStatusByVerifyPhone(this.refParam)
        .subscribe((res: any)=>{
          if(res){
            this.type = "Success!";
            this.title= "Registration are success!"
            this.detail="The system will redirect to homepage."
            this.openDialog();
            setTimeout(() => {
              this.dialog.closeAll();
            }, 3000);
            setTimeout(() => {
              this.router.navigate(['/homepage']);
            }, 3500);
          }else{console.log("update status "+false);}
        });
    }else{
      this.type = "Error";
      this.title= "Verification code is invalid."
      this.detail="The system will sending new one-time password to "+this.account.phonenumber+" again."
      this.openDialog();
      setTimeout(() => {
        this.dialog.closeAll();
      }, 5000);
      setTimeout(() => {
        this.ngOnInit();
      }, 5000);
    }
  }

}
