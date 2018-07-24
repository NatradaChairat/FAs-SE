import {RouterModule, Routes} from "@angular/router";
import {HomepageComponent} from "./homepage/homepage.component";
import {FilenotfoundComponent} from "./filenotfound/filenotfound.component";
import {NgModule} from "@angular/core";
import {ConfirmedEmailComponent} from "./confirmedemail/confirmedemail.component";
import {WaitComponent} from "./wait/wait.component";
import {EmailRegistrationComponent} from "./emailregistration/emailregistration.component";
import {InforegistrationComponent} from "./inforegistration/inforegistration.component";
import {VideoregistrationComponent} from "./videoregistration/videoregistration.component";

const appRoutes: Routes = [
  {path: '', redirectTo: '/homepage', pathMatch: 'full'},
  {path: 'homepage', component: HomepageComponent},
  {path: 'emailregistration', component: EmailRegistrationComponent},
  {path: 'inforegistration/:accountId', component: InforegistrationComponent},
  {path: 'videoregistration/:accountId', component: VideoregistrationComponent},
  {path: 'waiting', component: WaitComponent},
  /*{path: 'confirmedemail/:email/:username/:localtime', component: ConfirmedEmailComponent},*/
  {path: 'confirmedemail/:key/:localtime', component: ConfirmedEmailComponent},
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
