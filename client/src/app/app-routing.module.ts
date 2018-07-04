import {RouterModule, Routes} from "@angular/router";
import {HomepageComponent} from "./homepage/homepage.component";
import {FilenotfoundComponent} from "./filenotfound/filenotfound.component";
import {NgModule} from "@angular/core";
import {ConfirmedemailComponent} from "./confirmedemail/confirmedemail.component";
import {WaitComponent} from "./wait/wait.component";
import {EmailRegistrationComponent} from "./emailregistration/emailregistration.component";

const appRoutes: Routes = [
  {path: '', redirectTo: '/homepage', pathMatch: 'full'},
  {path: 'homepage', component: HomepageComponent},
  {path: 'emailregistration', component: EmailRegistrationComponent},
  {path: 'waiting', component: WaitComponent},
  {path: 'comfirmedemail', component: ConfirmedemailComponent},
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
