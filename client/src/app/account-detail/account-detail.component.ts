import { Component, OnInit } from '@angular/core';
import {Account} from "../entity/Account";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {AccountDataServerService} from "../service/account-data-server.service";

@Component({
  selector: 'app-account-detail',
  templateUrl: './account-detail.component.html',
  styleUrls: ['./account-detail.component.css']
})
export class AccountDetailComponent implements OnInit {

  account : Account;
  refParam: string;
  constructor(private router: Router, private route:ActivatedRoute, private accountDataServerService: AccountDataServerService) { }

  ngOnInit() {
    this.route.params.subscribe((params: Params) =>{
      this.refParam = params['key'];
      this.accountDataServerService.getAccountByParam(params['key'])
        .subscribe((res: any)=>{
            this.account = res;
          },err => {
            //this.reSendEmail(params['key']);
          }
        );
    });
  }

  accept(){
    this.account.status = "approved";
    this.accountDataServerService.updateStatus(this.account)
      .subscribe((res: any)=> {
        if(res){
          this.accountDataServerService.sendResultAuthenProcessToEmail(this.refParam,"approved")
            .subscribe((result:any) =>{
              if(result){
                this.router.navigate(['/staffDashboard']);
              }
            });

        }else {
          this.accept();
        }
      },(error: any) => {
        console.log(error);
      })
  }

  reject(){
    this.account.status = "disapproved";
    this.accountDataServerService.updateStatus(this.account)
      .subscribe((res: any)=> {
        if(res){
          this.accountDataServerService.sendResultAuthenProcessToEmail(this.refParam,"disapproved")
            .subscribe((result:any) =>{
              if(result){
                this.router.navigate(['/staffDashboard']);
              }
            });
        }else {
          this.reject();
        }
      },(error: any) => {
        console.log(error);
      })
  }

}
