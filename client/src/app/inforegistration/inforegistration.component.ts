import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {ActivatedRoute, Params, Router, RouterEvent} from "@angular/router";
import {AccountDataServerService} from "../service/account-data-server.service";
import {MatDialog} from "@angular/material";
import {Account} from "../entity/Account";
import {DialogComponent} from "../dialog/dialog.component";
export interface DialogData{
  type: string;
  title: string
  detail: string;
}
@Component({
  selector: 'app-inforegistration',
  templateUrl: './inforegistration.component.html',
  styleUrls: ['./inforegistration.component.css']
})
export class InforegistrationComponent implements OnInit {
  account: any = {};
  refParam: string;
  infoRegistrerForm: FormGroup;
  type: string;
  title: string;
  detail: string;
  constructor(private route:ActivatedRoute, private formBuilder: FormBuilder, private router: Router, private accountDataServerService: AccountDataServerService, private dialog: MatDialog ) { }

  ngOnInit() {
    this.account = new Account();
    this.route.params.subscribe((param: Params) =>{
      this.refParam = (param['param']);
    })
    console.log(this.refParam);
  }

  openDialog():void{
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '350px',
      disableClose: true,
      data: {type: this.type,title:this.title, detail: this.detail}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  onSubmit(account:Account){
    //console.log(account);
    //checkStudentIdIsRepeat
    /*if(this.checkDuplicatedStudentId(account)){
      console.log("Duplicated StudentID")
    }else{
      if(this.checkDuplicatedPhonenumber(account)){
        console.log("Duplicated Phonenumber")
      }else{
        this.accountDataServerService.sendPersonalAccount(account)
          .subscribe((res: any)=>{
            console.log("sendPersonalAccount "+res);
            if(res){
              setTimeout(() => {
                this.router.navigate(['/videoregidtration/']);
              }, 1000);
            }else{console.log("sendPersonalAccount "+false);}
          });
      }
    }*/

    /*this.checkDuplicatedStudentId(account)
      .then( result=> {
        if(result){
          console.log("studentId duplicated");
        }else{
          this.checkDuplicatedPhonenumber(account)
            .then( result2 => {
              if(result2){
                console.log("Phonenumber duplicated");
              }else{
                setTimeout(() => {
                  this.router.navigate(['/videoregidtration/']);
                }, 1000);
              }
            });
        }
      });*/

    this.checkDuplicatedStudentId(account);

  }

  checkDuplicatedStudentId(account: Account): any{
    this.accountDataServerService.checkDuplicatedStudentId(account.studentId)
      .subscribe(data=>{
        //console.log(data);
        if(data){
          //return true;
          this.type = "Error";
          this.title= "Can not submit the form."
          this.detail="Student ID is duplicated."
          this.openDialog();
        }else{
          //return false;
          this.checkDuplicatedPhonenumber(account);
        }
      });

  }

  checkDuplicatedPhonenumber(account: Account): any{
    this.accountDataServerService.checkDuplicatedPhonenumber(account.phonenumber)
      .subscribe(data=>{
        //console.log(data);
        if(data){
          //return true;
          this.type = "Error";
          this.title= "Can not submit the form."
          this.detail="Phone number is duplicated."
          this.openDialog();
        }else{
          //return false;
          this.accountDataServerService.sendPersonalAccount(account,this.refParam)
            .subscribe((res: any)=>{
              //console.log(res);
              if(res){
                setTimeout(() => {
                  this.router.navigate(['/videoRegistration/'+this.refParam]);
                }, 1000);
              }else{console.log("sendPersonalAccount "+false);}
            });
        }
      });
  }

  /*onSubmit(account:Account){
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
  }*/

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
