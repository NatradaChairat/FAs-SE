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
  account: Account;
  isNoData: boolean;
  buttonDisabled = true;
  constructor( private route:ActivatedRoute, private router: Router, private accountDataServerService: AccountDataServerService) {

  }

  ngOnInit() {
    console.log(window.location.href);
    this.route.params.subscribe((params: Params) =>{
      console.log(params['key']+ "/"+ params['localtime'])
      this.accountDataServerService.updateAccountByParam(params['key'], params['localtime'])
        .subscribe((account: Account)=>{
          console.log(account);
          if(account ==null){
            console.log("null");
            this.isNoData = true;
          }else {
            console.log("not null");
            this.account = account;
            this.buttonDisabled = false;
          }
        },err => {this.isNoData = true;}
        );
    });
    /*this.route.params.subscribe((params: Params) => {
      this.email = params['email'];
      this.username = params['username'];
      this.localtime = params['localtime'];
      console.log(this.username+ " "+ this.localtime);
    });

    this.accountDataServerService.updateStatusByEmailUsername(this.email,this.username,this.localtime)
      .subscribe(data =>{});*/
    /*this.route.paramMap.pipe(switchMap((params:Params)=> {
      this.accountDataServerService.getStatusByAccountId(params['username'],params['localtime'])
        .subscribe(result =>{
          console.log("result");
        })
    }));*/

  }

  infoRegistration(){
    console.log("go to register step2 "+this.account.accountId);
    this.router.navigate(['/inforegistration/'+this.account.accountId]);

  }
}
