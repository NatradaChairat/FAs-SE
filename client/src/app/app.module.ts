import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { HomepageComponent } from './homepage/homepage.component';
import { FilenotfoundComponent } from './filenotfound/filenotfound.component';
import {AppRoutingModule} from "./app-routing.module";
import {FormsModule, ReactiveFormsModule, FormGroup} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {AccountDataServerService} from "./service/account-data-server.service";
import { ConfirmedEmailComponent } from './confirmedemail/confirmedemail.component';
import { WaitComponent } from './wait/wait.component';
import {EmailRegistrationComponent} from './emailregistration/emailregistration.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MAT_DIALOG_DEFAULT_OPTIONS, MatDialogModule} from "@angular/material";
import { EmailRegistrationDiologComponent } from './emailregistration-diolog/emailregistration-diolog.component';

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    FilenotfoundComponent,
    ConfirmedEmailComponent,
    WaitComponent,
    EmailRegistrationComponent,
    EmailRegistrationDiologComponent
  ],
  entryComponents: [
    EmailRegistrationDiologComponent
  ],
  imports: [
    BrowserModule,AppRoutingModule,FormsModule,HttpClientModule,ReactiveFormsModule,BrowserAnimationsModule,MatDialogModule
  ],
  providers: [AccountDataServerService,  {provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: false}}],
  bootstrap: [AppComponent]
})
export class AppModule { }
