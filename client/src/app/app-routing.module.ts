import {RouterModule, Routes} from "@angular/router";
import {HomepageComponent} from "./homepage/homepage.component";
import {FilenotfoundComponent} from "./filenotfound/filenotfound.component";
import {NgModule} from "@angular/core";
import {ConfirmedEmailComponent} from "./confirmedemail/confirmedemail.component";
import {WaitComponent} from "./wait/wait.component";
import {EmailRegistrationComponent} from "./emailregistration/emailregistration.component";
import {InforegistrationComponent} from "./inforegistration/inforegistration.component";
import {FaceregistrationComponent} from "./faceregistration/faceregistration.component";
import {PhonenoVerificationComponent} from "./phoneno-verification/phoneno-verification.component";
import {EmailLoginComponent} from "./email-login/email-login.component";
import {FaceLoginComponent} from "./face-login/face-login.component";
import {StaffDashboardComponent} from "./staff-dashboard/staff-dashboard.component";
import {AccountDetailComponent} from "./account-detail/account-detail.component";
import {FaceLoginSuccessComponent} from "./face-login-success/face-login-success.component";
import {EmailLoginSuccessComponent} from "./email-login-success/email-login-success.component";
import {SubmitSuccessComponent} from "./submit-success/submit-success.component";
import {TrainImageComponent} from "./train-image/train-image.component";
import {OtpComponent} from "./otp/otp.component";

const appRoutes: Routes = [
  {path: '', redirectTo: '/homepage', pathMatch: 'full'},
  {path: 'homepage', component: HomepageComponent},
  {path: 'emailRegistration', component: EmailRegistrationComponent},
  {path: 'infoRegistration/:param', component: InforegistrationComponent},
  {path: 'faceRegistration/:param', component: FaceregistrationComponent},
  {path: 'phonenoVerification/:param', component: PhonenoVerificationComponent},
  {path: 'waiting', component: WaitComponent},
  /*{path: 'confirmedemail/:email/:username/:localtime', component: ConfirmedEmailComponent},*/
  {path: 'confirmedemail/:key', component: ConfirmedEmailComponent},
  {path: 'emailLogin', component: EmailLoginComponent},
  {path: 'faceLogin', component: FaceLoginComponent},
  {path: 'faceLoginSuccess', component: FaceLoginSuccessComponent},
  {path: 'emailLoginSuccess', component: EmailLoginSuccessComponent},
  {path: 'submitSuccess', component: SubmitSuccessComponent},
  {path: 'staffDashboard', component: StaffDashboardComponent},
  {path: 'detail/:key', component: AccountDetailComponent},
  {path: 'oneTimePassword', component: OtpComponent},
  {path: 'trainFace/:param', component: TrainImageComponent},
  {path: '**', component: FilenotfoundComponent}
];

@NgModule({
  imports: [
    RouterModule.forRoot(appRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {
}
