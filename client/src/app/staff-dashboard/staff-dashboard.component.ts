import { Component, OnInit } from '@angular/core';
import {AccountDataServerService} from "../service/account-data-server.service";
import {Router} from "@angular/router";
import {Account} from "../entity/Account";

@Component({
  selector: 'app-staff-dashboard',
  templateUrl: './staff-dashboard.component.html',
  styleUrls: ['./staff-dashboard.component.css']
})
export class StaffDashboardComponent implements OnInit {

  verifiedAccounts: Account[];

  constructor(private accountDataServerService: AccountDataServerService, private router: Router) { }

  ngOnInit() {
    this.accountDataServerService.getAccountByStatus("verified")
      .subscribe((accounts:any) => this.verifiedAccounts = accounts,
        (error) =>{
          if(error.status === 401){
            console.log("no content")
          }
        });
  }

  showDetail(account: Account){
    console.log("go show detail")
  }

}
