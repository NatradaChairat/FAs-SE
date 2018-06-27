import {RouterModule, Routes} from "@angular/router";
import {HomepageComponent} from "./homepage/homepage.component";
import {RegisterstepOneComponent} from "./registerstep-one/registerstep-one.component";
import {FilenotfoundComponent} from "./filenotfound/filenotfound.component";
import {NgModule} from "@angular/core";

const appRoutes: Routes = [
  {path: '', redirectTo: '/homepage', pathMatch: 'full'},
  {path: 'homepage', component: HomepageComponent},
  {path: 'registerstep1', component: RegisterstepOneComponent},
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
