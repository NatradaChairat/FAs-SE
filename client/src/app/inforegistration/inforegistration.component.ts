import { Component, OnInit } from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {Router, RouterEvent} from "@angular/router";
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
  constructor(private formBuilder: FormBuilder, private router: Router, private accountDataServerService: AccountDataServerService, private dialog: MatDialog ) { }

  ngOnInit() {
    this.account = new Account();
  }

  onSubmit(account: Account){
    console.log(account);

  }



}
