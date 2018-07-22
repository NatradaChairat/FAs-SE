import { Component, OnInit } from '@angular/core';
import {Account} from "../entity/Account";
import {AccountDataServerService} from "../service/account-data-server.service";
import {ActivatedRoute, ParamMap, Params} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {Observable} from "rxjs/Observable";

@Component({
  selector: 'app-confirmedemail',
  templateUrl: './confirmedemail.component.html',
  styleUrls: ['./confirmedemail.component.css']
})
export class ConfirmedEmailComponent implements OnInit {

  username: string;
  email: string;
  localtime:string;

  constructor( private route:ActivatedRoute, private accountDataServerService: AccountDataServerService) {

  }

  ngOnInit() {
    console.log(window.location.href);
    this.route.params.subscribe((params: Params) =>{
      this.accountDataServerService.getAccountByParam(params['key'], params['localtime'])
        .subscribe(data=>{
          console.log("Data: "+data);
        })
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

  registerstep2(){
    console.log("go to register step2");
  }
}
