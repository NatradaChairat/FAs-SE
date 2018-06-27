import { Component, OnInit } from '@angular/core';
import {Account} from "../model/Account";

@Component({
  selector: 'app-registerstep-one',
  templateUrl: './registerstep-one.component.html',
  styleUrls: ['./registerstep-one.component.css']
})
export class RegisterstepOneComponent implements OnInit {
  account: any = {};
  constructor() { }

  ngOnInit() {
    this.account = new Account();
  }

  register(account: Account){
    console.log(account);
  }

}
