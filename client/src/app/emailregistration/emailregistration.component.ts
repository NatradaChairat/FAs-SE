import { Component, OnInit } from '@angular/core';
import {AccountDataServerService} from "../service/account-data-server.service";
import {Router} from "@angular/router";
import {Account} from "../entity/Account";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-emailregistration',
  templateUrl: './emailregistration.component.html',
  styleUrls: ['./emailregistration.component.css']
})
export class EmailRegistrationComponent implements OnInit {
  registerForm: FormGroup;
  account: any = {};
  //submitted = false;

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

  /*get f(){
    return this.registerForm.controls;
  }*/

  /*checkForm(){
    this.registerForm = this.formBuilder.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(6)]]
    });

  }*/

  // convenience getter for easy access to form fields
  //get check(){return this.registerForm.controls;}


  register(account: Account){
    //this.submitted = true;
    console.log(account);
    //if(this.registerForm.valid) {
      this.accountDataServerService.sendAccount(account)
        .subscribe(data => {
            console.log(data);
            /*this.router.navigate(['/waiting']);*/
          }
          , error => console.log(error));
    /*}
    else{
      console.log("Form is invalid");
    }*/

  }

}
