import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {ActivatedRoute, Params, Router, RouterEvent} from "@angular/router";
import {AccountDataServerService} from "../service/account-data-server.service";
import {MatDialog} from "@angular/material";
import {Account} from "../entity/Account";

@Component({
  selector: 'app-inforegistration',
  templateUrl: './inforegistration.component.html',
  styleUrls: ['./inforegistration.component.css']
})
export class InforegistrationComponent implements OnInit {
  account: any = {};
  accountId: string;
  infoRegistrerForm: FormGroup;
  constructor(private route:ActivatedRoute, private formBuilder: FormBuilder, private router: Router, private accountDataServerService: AccountDataServerService, private dialog: MatDialog ) { }

  ngOnInit() {
    this.account = new Account();
    this.route.params.subscribe((param: Params) =>{
      this.accountId = param['accountId'];
    })
    console.log(this.accountId);
  }

  onSubmit(account:Account){
    console.log(account);
    account.accountId = this.accountId;
    //checkStudentIdIsRepeat
    this.accountDataServerService.getAccountByStudentId(account.studentId)
      .subscribe(data=>{
        console.log(data);
        if(data==null){
          this.checkPhonenumberIsRepeat(account);
        }else{
          console.log("Repeated data: StudentId");
        }
      });
  }

  checkPhonenumberIsRepeat(account: Account){
    this.accountDataServerService.getAccountByPhonenumber(account.phonenumber)
      .subscribe(data=>{
        console.log(data);
        if(data==null){
          this.accountDataServerService.sendPersonalAccount(account)
            .subscribe(data => {
              console.log(data);
              if(data!=null){
                console.log("Return data");
                console.log(data);
              }else{
                console.log("Null");
                this.router.navigate(['/videoregistration/'+this.account.accountId]);
              }
            });
        }else{
          console.log("Repeated data: phonenumber");
        }
      });
  }

  /*checkStudentIdIsRepeat(account: Account){
    this.accountDataServerService.getAccountByStudentId(account.studentId)
      .subscribe(data=>{
        console.log(data);
        if(data==null){
          this.checkPhonenumberIsRepeat(account);
        }else{
          console.log("Repeated data: StudentId");
        }
      });
  }*/




}
