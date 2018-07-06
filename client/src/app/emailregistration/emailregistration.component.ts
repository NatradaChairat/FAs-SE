import { Component, OnInit } from '@angular/core';
import {AccountDataServerService} from "../service/account-data-server.service";
import {Router} from "@angular/router";
import {Account} from "../entity/Account";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Config} from "protractor";

@Component({
  selector: 'app-emailregistration',
  templateUrl: './emailregistration.component.html',
  styleUrls: ['./emailregistration.component.css']
})
export class EmailRegistrationComponent implements OnInit {
  registerForm: FormGroup;
  account: any = {};

  constructor(private formBuilder: FormBuilder, private router: Router, private accountDataServerService: AccountDataServerService) {

  }

  ngOnInit() {
    this.account = new Account();

    /*this.registerForm = this.formBuilder.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(6)]]
    });*/
  }

  register(account: Account){
    console.log(account);
    console.log(this.usernameIsRepeated(account.username)+" Test");
    /*this.accountDataServerService.sendAccount(account)
      .subscribe(data => {
        console.log(data);
            /!*this.router.navigate(['/waiting']);*!/
        },error => console.log(error));
*/
  }
  result: Boolean;
  usernameIsRepeated(username: String): Boolean {
    this.accountDataServerService.getAccountByUsername(username)
      .subscribe(response =>{
        if(response==null){
          this.result = false;
        }else {this.result =true;}

        console.log(this.result)
      },error => {
        console.log("errer: "+error);

      });
    return this.result;
  }
}
