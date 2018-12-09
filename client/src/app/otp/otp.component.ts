import {Component, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {IntermediaryService} from "../service/intermediary.service";
import {Account} from "../model/Account.model";
import {AccountDataServerService} from "../service/account-data-server.service";

@Component({
  selector: 'app-otp',
  templateUrl: './otp.component.html',
  styleUrls: ['./otp.component.css']
})
export class OtpComponent implements OnInit {

  otpForm: FormGroup;
  timeout: boolean;
  account: any = {};
  otp: string;
  refCode: string;
  model: string;
  refParam: string;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private intermediaryService: IntermediaryService,
              private accountDataServerService: AccountDataServerService) {
  }

  ngOnInit() {
    console.log('OTP');
    this.timeout = false;
    this.refCode = '';
    this.otp = '';
    this.model = '';
    this.account = new Account();
    this.account.phonenumber = this.intermediaryService.getPhoneNumber();

    setTimeout(() => {
      this.timeout = true;
    }, 300000);

    this.accountDataServerService.getVerifyPhonenumberCode(this.account.phonenumber)
      .subscribe((data: any) => {
          console.log(data);
          this.account.phonenumber = data.body.phonenumber.toString();
          this.refCode = data.body.refCode.toString();
          this.otp = data.body.otp.toString();

        }
      );
  }

  onSubmit(otp: string) {
    console.log('show isTimeout ' + this.timeout);
    if (this.otp === otp && !this.timeout) {
      console.log(this.account);
      this.router.navigate(['/trainFace/1']);
    } else {
      if (window.confirm('Verification code is invalid.\n ' +
        'The system will sending new one-time password to ' + this.account.phonenumber + ' again.')) {
        setTimeout(() => {
          this.ngOnInit();
        }, 5000);
      }
    }
  }

}
