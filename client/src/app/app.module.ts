import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { HomepageComponent } from './homepage/homepage.component';
import { FilenotfoundComponent } from './filenotfound/filenotfound.component';
import {AppRoutingModule} from "./app-routing.module";
import {FormsModule, ReactiveFormsModule, FormGroup} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {AccountDataServerService} from "./service/account-data-server.service";
import { ConfirmedemailComponent } from './confirmedemail/confirmedemail.component';
import { WaitComponent } from './wait/wait.component';
import { EmailRegistrationComponent } from './emailregistration/emailregistration.component';

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    FilenotfoundComponent,
    ConfirmedemailComponent,
    WaitComponent,
    EmailRegistrationComponent
  ],
  imports: [
    BrowserModule,AppRoutingModule,FormsModule,HttpClientModule,ReactiveFormsModule
  ],
  providers: [AccountDataServerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
