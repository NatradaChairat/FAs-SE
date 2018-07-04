import { Component, OnInit } from '@angular/core';
import {Account} from "../entity/Account";
import {Router} from "@angular/router";
import {AccountDataServerService} from "../service/account-data-server.service";
import {Observable} from "rxjs/index";

@Component({
  selector: 'app-registerstep-one',
  templateUrl: './registerstep-one.component.html',
  styleUrls: ['./registerstep-one.component.css']
})
export class RegisterstepOneComponent implements OnInit {
  account: any = {};
  constructor(private router: Router, private accountDataServerService: AccountDataServerService) { }

  ngOnInit() {
    this.account = new Account();
  }

  register(account: Account){

    console.log(account);
    /*this.accountDataServerService.sendAccount(account)
      .subscribe(data =>{
        return true;
        },
        error => {
        console.error("Error saving account" );

        return Observable.throw(error);
        });*/

    this.accountDataServerService.sendAccount(account)
      .subscribe(data => {console.log(data);
          /*this.router.navigate(['/waiting']);*/}
        , error => console.log(error));


  }

}
