import {RouterModule, Routes} from "@angular/router";
import {HomepageComponent} from "./homepage/homepage.component";
import {FilenotfoundComponent} from "./filenotfound/filenotfound.component";
import {NgModule} from "@angular/core";
import {ConfirmedEmailComponent} from "./confirmedemail/confirmedemail.component";
import {WaitComponent} from "./wait/wait.component";
import {EmailRegistrationComponent} from "./emailregistration/emailregistration.component";
import {InforegistrationComponent} from "./inforegistration/inforegistration.component";
import {VideoregistrationComponent} from "./videoregistration/videoregistration.component";
import {PhonenoVerificationComponent} from "./phoneno-verification/phoneno-verification.component";
import {EmailLoginComponent} from "./email-login/email-login.component";
import {FaceLoginComponent} from "./face-login/face-login.component";

const appRoutes: Routes = [
  {path: '', redirectTo: '/homepage', pathMatch: 'full'},
  {path: 'homepage', component: HomepageComponent},
  {path: 'emailRegistration', component: EmailRegistrationComponent},
  {path: 'infoRegistration/:param', component: InforegistrationComponent},
  {path: 'videoRegistration/:param', component: VideoregistrationComponent},
  {path: 'phonenoVerification/:param', component: PhonenoVerificationComponent},
  {path: 'waiting', component: WaitComponent},
  /*{path: 'confirmedemail/:email/:username/:localtime', component: ConfirmedEmailComponent},*/
  {path: 'confirmedemail/:key', component: ConfirmedEmailComponent},
  {path: 'email-login', component: EmailLoginComponent},
  {path: 'face-login', component: FaceLoginComponent},
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
export class AppRoutingModule{

}
