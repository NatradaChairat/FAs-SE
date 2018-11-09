import { Component, OnInit } from '@angular/core';
import {AccountDataServerService} from "../service/account-data-server.service";
import {Router} from "@angular/router";
import {Account} from "../model/Account.model";

@Component({
  selector: 'app-staff-dashboard',
  templateUrl: './staff-dashboard.component.html',
  styleUrls: ['./staff-dashboard.component.css']
})
export class StaffDashboardComponent implements OnInit {

  verifiedAccounts: Account[];
  disapprovedAccounts: Account[];

  constructor(private accountDataServerService: AccountDataServerService, private router: Router) { }

  ngOnInit() {
    this.accountDataServerService.getAccountByStatus("verified")
      .subscribe((accounts:any) => {
          this.verifiedAccounts = accounts;
          this.accountDataServerService.getAccountByStatus("disapproved")
            .subscribe((accounts:any) => {
            this.disapprovedAccounts = accounts;
        },(error) =>{
              if(error.status === 401){
                console.log("no content")
              }
            });
      },(error) =>{
          if(error.status === 401){
            console.log("no content")
          }
        });

  }

  showDetail(url: String){
    this.router.navigate(['detail/'+url]);
  }

}
