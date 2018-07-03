import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { RegisterstepOneComponent } from './registerstep-one/registerstep-one.component';
import { HomepageComponent } from './homepage/homepage.component';
import { FilenotfoundComponent } from './filenotfound/filenotfound.component';
import {AppRoutingModule} from "./app-routing.module";
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {AccountDataServerService} from "./service/account-data-server.service";
import { ConfirmedemailComponent } from './confirmedemail/confirmedemail.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterstepOneComponent,
    HomepageComponent,
    FilenotfoundComponent,
    ConfirmedemailComponent
  ],
  imports: [
    BrowserModule,AppRoutingModule,FormsModule,HttpClientModule
  ],
  providers: [AccountDataServerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
