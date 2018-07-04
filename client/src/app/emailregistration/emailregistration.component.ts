import { Component, OnInit } from '@angular/core';
import {AccountDataServerService} from "../service/account-data-server.service";
import {Router} from "@angular/router";
import {Account} from "../entity/Account";

@Component({
  selector: 'app-emailregistration',
  templateUrl: './emailregistration.component.html',
  styleUrls: ['./emailregistration.component.css']
})
export class EmailRegistrationComponent implements OnInit {
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
