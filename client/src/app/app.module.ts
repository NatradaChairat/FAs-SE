import {BrowserModule} from '@angular/platform-browser';
import {NgModule, NO_ERRORS_SCHEMA} from '@angular/core';
import {AppComponent} from './app.component';
import {HomepageComponent} from './homepage/homepage.component';
import {FilenotfoundComponent} from './filenotfound/filenotfound.component';
import {AppRoutingModule} from './app-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from "@angular/common/http";
import {AccountDataServerService} from "./service/old/account-data-server.service";
import {ConfirmedEmailComponent} from './confirmedemail/confirmedemail.component';
import {WaitComponent} from './wait/wait.component';
import {EmailRegistrationComponent} from './emailregistration/emailregistration.component';
import {BrowserAnimationsModule, NoopAnimationsModule} from "@angular/platform-browser/animations";
import {
  MAT_DIALOG_DEFAULT_OPTIONS,
  MatAutocompleteModule, MatButtonModule,
  MatDialogModule, MatIconModule,
  MatNativeDateModule, MatProgressBarModule,
  MatRadioModule
} from "@angular/material";
import {DialogComponent} from './dialog/dialog.component';
import {InforegistrationComponent} from './inforegistration/inforegistration.component';
import {WebcamComponent} from './webcam/webcam.component';
import {FaceregistrationComponent} from './faceregistration/faceregistration.component';
import {PhonenoVerificationComponent} from './phoneno-verification/phoneno-verification.component';
import {FaceLoginComponent} from './face-login/face-login.component';
import {EmailLoginComponent} from './email-login/email-login.component';
import {StaffDashboardComponent} from './staff-dashboard/staff-dashboard.component';
import {AccountDetailComponent} from './account-detail/account-detail.component';
import {VgCoreModule} from "videogular2/core";
import {VgControlsModule} from "videogular2/controls";
import {VgOverlayPlayModule} from "videogular2/overlay-play";
import {VgBufferingModule} from "videogular2/buffering";
import {FileSelectDirective} from "ng2-file-upload";
import {AngularFireModule} from "@angular/fire";
import {environment} from "../environments/environment";
import {AngularFireAuthModule} from "@angular/fire/auth";
import {AuthenticationService} from "./service/authentication.service";
import {WebcamModule} from "ngx-webcam";
import {FirebaseService} from "./service/firebase.service";
import {AngularFireStorageModule} from "@angular/fire/storage";
import {FaceRecognitionService} from "./service/face-recognition.service";
import {SubmitSuccessComponent} from './submit-success/submit-success.component';
import {FaceLoginSuccessComponent} from './face-login-success/face-login-success.component';
import {EmailLoginSuccessComponent} from './email-login-success/email-login-success.component';
import {IntermediaryService} from "./service/intermediary.service";
import { TrainImageComponent } from './train-image/train-image.component';
import { OtpComponent } from './otp/otp.component';
import {SlideshowModule} from "ng-simple-slideshow";


@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    FilenotfoundComponent,
    ConfirmedEmailComponent,
    WaitComponent,
    EmailRegistrationComponent,
    DialogComponent,
    InforegistrationComponent,
    WebcamComponent,
    FaceregistrationComponent,
    PhonenoVerificationComponent,
    FaceLoginComponent,
    EmailLoginComponent,
    StaffDashboardComponent,
    AccountDetailComponent,
    FileSelectDirective,
    SubmitSuccessComponent,
    FaceLoginSuccessComponent,
    EmailLoginSuccessComponent,
    TrainImageComponent,
    OtpComponent
  ],
  entryComponents: [
    DialogComponent,
    FaceLoginSuccessComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatAutocompleteModule,
    MatRadioModule,
    MatNativeDateModule,
    MatProgressBarModule,
    MatButtonModule,
    MatIconModule,
    ReactiveFormsModule,
    NoopAnimationsModule,
    VgCoreModule,
    VgControlsModule,
    VgOverlayPlayModule,
    VgBufferingModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFireAuthModule,
    AngularFireStorageModule,
    WebcamModule,
    SlideshowModule
  ],
  providers: [AccountDataServerService,
    {provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: false}},
    AuthenticationService,
    FirebaseService,
    FaceRecognitionService,
    IntermediaryService],
  bootstrap: [AppComponent],
  schemas: [NO_ERRORS_SCHEMA],
})
export class AppModule {
}
