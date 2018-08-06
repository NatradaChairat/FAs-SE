import { Component, OnInit } from '@angular/core';
import {FormGroup} from "@angular/forms";
import {Account} from "../entity/Account";
import {ActivatedRoute, Params} from "@angular/router";

@Component({
  selector: 'app-phoneno-verification',
  templateUrl: './phoneno-verification.component.html',
  styleUrls: ['./phoneno-verification.component.css']
})
export class PhonenoVerificationComponent implements OnInit {
  otpForm: FormGroup;
  account: any = {};
  otp: string;
  refParam: string;
  constructor(private route:ActivatedRoute) { }

  ngOnInit() {
    this.account = new Account();
    this.route.params.subscribe((param: Params) =>{
      this.refParam = (param['param']);
    })
    console.log(this.refParam);


  }

  onSubmit(otp: string){

  }

}
