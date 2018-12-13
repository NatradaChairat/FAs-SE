import {Component, OnInit} from '@angular/core';
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
  trainingAccounts: Account[];

  constructor(private accountDataServerService: AccountDataServerService, private router: Router) {
  }

  ngOnInit() {
    this.accountDataServerService.getAccountByStatus('verified')
      .subscribe((approvedAccounts: any) => {
        this.verifiedAccounts = approvedAccounts;
        this.accountDataServerService.getAccountByStatus('disapproved')
          .subscribe((rejectedAccounts: any) => {
            this.disapprovedAccounts = rejectedAccounts;
            this.accountDataServerService.getAccountByStatus('training')
              .subscribe((training: any) => {
                this.trainingAccounts = training;
              });
          }, (error) => {
            if (error.status === 401) {
              console.log('no content');
            }
          });
      }, (error) => {
        if (error.status === 401) {
          console.log('no content');
        }
      });

  }

  showDetail(url: String) {
    this.router.navigate(['detail/' + url]);
  }

}
