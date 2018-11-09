import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {WebcamImage} from "../webcam/domain/webcam-images";
import {Subject} from "rxjs/Subject";
import {Observable} from "rxjs/Observable";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {AccountDataServerService} from "../service/account-data-server.service";
import {InforegistrationComponent} from "../inforegistration/inforegistration.component";
import {WebcamInitError} from '../webcam/domain/webcam-init-error';


@Component({
  selector: 'app-videoregistration',
  templateUrl: './videoregistration.component.html',
  styleUrls: ['./videoregistration.component.css']
})


export class VideoregistrationComponent implements OnInit,AfterViewInit {

  account: any = {};
  randomtext: string;
  refParam: string;

  @ViewChild(InforegistrationComponent) viewInfoComp;

  constructor(private route:ActivatedRoute,
              private router:Router,
              private accountDataServerService: AccountDataServerService) { }

  ngOnInit() {
    this.randomtext = this.randomText();
    this.route.params.subscribe((param: Params) =>{
      this.refParam = (param['param']);
    })
    console.log(this.refParam);
  }
  randomText(){
    let text: string = "";
    let charset: string = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    for(let i =0; i<6 ; i++){
      text += charset.charAt(Math.floor(Math.random() * charset.length));
    }
    return text;
  }

  ngAfterViewInit() {
  }


  onSubmit(){
    this.account = this.viewInfoComp.childAccount;
    console.log(this.account);
    this.router.navigate(['phonenoVerification/'+this.refParam]);
  }

}
