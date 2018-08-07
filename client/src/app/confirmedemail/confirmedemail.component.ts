import { Component, OnInit } from '@angular/core';
import {Account} from "../entity/Account";
import {AccountDataServerService} from "../service/account-data-server.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {collectExternalReferences} from "@angular/compiler";
import {error} from "util";
import {catchError} from "rxjs/operators";

@Component({
  selector: 'app-confirmedemail',
  templateUrl: './confirmedemail.component.html',
  styleUrls: ['./confirmedemail.component.css']
})

export class ConfirmedEmailComponent implements OnInit {
  account: any = {};
  isNoData: boolean;
  buttonDisabled = true;
  refParam : string;
  constructor( private route:ActivatedRoute, private router: Router, private accountDataServerService: AccountDataServerService) {

  }

  ngOnInit() {
    this.account = new Account();
    //console.log(window.location.href);
    this.route.queryParams.subscribe((params: Params) =>{
      //console.log(params['id']+ "/"+ params['time'])
      this.refParam = params['id'];
      this.accountDataServerService.updateStatusByParam(params['id'],params['time'])
        .subscribe((res: any)=>{
              this.account = res;
              this.buttonDisabled = false;
          },err => {
            this.isNoData = true;
            this.reSendEmail(this.refParam);
            //this.reSendEmail(params['key']);
          }
        );
    });
    /*this.route.params.subscribe((params: Params) =>{
      console.log(params['key']+ "/"+ params['localtime'])
      this.accountDataServerService.updateAccountByParam(params['key'], params['localtime'])
        .subscribe((account: Account)=>{
          console.log(account);
          if(account ==null){
            console.log("null");
            this.isNoData = true;
            this.reSendEmail(params['key']);
          }else {
            console.log("not null");
            this.account = account;
            this.buttonDisabled = false;
          }
        },err => {
          this.isNoData = true;
          this.reSendEmail(params['key']);
          }
        );
    });*/


  }
  reSendEmail(key: string){
    this.accountDataServerService.sendEmail(key).subscribe(
      (account: Account)=>{
        setTimeout(() =>
          {
            this.router.navigate(['/waiting']);
          },
          3000);
      });
  }

  infoRegistration(){
    /*console.log("go to register step2 "+encodeURIComponent(this.refParam));*/
    this.router.navigate(['/infoRegistration/'+encodeURIComponent(this.refParam)]);

  }
}
