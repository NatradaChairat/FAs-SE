import { Component, OnInit } from '@angular/core';
import {FormGroup} from "@angular/forms";
import {Account} from "../entity/Account";
import {ActivatedRoute, Params} from "@angular/router";
import {AccountDataServerService} from "../service/account-data-server.service";

@Component({
  selector: 'app-phoneno-verification',
  templateUrl: './phoneno-verification.component.html',
  styleUrls: ['./phoneno-verification.component.css']
})
export class PhonenoVerificationComponent implements OnInit {
  otpForm: FormGroup;
  account: any = {};
  otp: string;
  refCode: string;
  refParam: string;

  constructor(private route:ActivatedRoute,  private accountDataServerService: AccountDataServerService) { }

  ngOnInit() {
    this.account = new Account();
    this.route.params.subscribe((param: Params) =>{
      this.refParam = (param['param']);
    })
    console.log(this.refParam);

    this.accountDataServerService.getVerifyPhonenumberCode(this.refParam)
      .subscribe((data: any)=>{
        console.log(data.body.otp.toString());
        console.log(data.body.refCode.toString());
        console.log(data.body.phonenumber.toString());
        this.account.phonenumber = data.body.phonenumber.toString();
        this.refCode = data.body.refCode.toString();

      }
    );


  }

  onSubmit(otp: string){
    if(this.otp ==otp){
      console.log("pass")
    }
  }

}
