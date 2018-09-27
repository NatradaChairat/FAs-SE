import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {WebcamImage} from "../webcam/domain/webcam-images";
import {Subject} from "rxjs/Subject";
import {Observable} from "rxjs/Observable";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {AccountDataServerService} from "../service/account-data-server.service";
import {InforegistrationComponent} from "../inforegistration/inforegistration.component";




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

  constructor(private route:ActivatedRoute,private router:Router, private accountDataServerService: AccountDataServerService) { }

  ngOnInit() {
    this.randomtext = this.randomText();
    this.route.params.subscribe((param: Params) =>{
      this.refParam = (param['param']);
    })
    console.log(this.refParam);
    //this.loadScript('/videoScript.js')
  }
  randomText(){
    let text: string = "";
    let charset: string = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    for(let i =0; i<6 ; i++){
      text += charset.charAt(Math.floor(Math.random() * charset.length));
    }
    return text;
  }
  //@ViewChild('videojs') videojs:any;
// note that "#video" is the name of the template variable in the video element

  ngAfterViewInit() {
    /*let _video=this.video.nativeElement;
    if(navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
      navigator.mediaDevices.getUserMedia({ video: true })
        .then(stream => {
          _video.src = window.URL.createObjectURL(stream);
          _video.play();
        })
    }*/
    /*let video:HTMLVideoElement = this.videojs.nativeElement;
    video.muted = false;
    video.controls = true;
    video.autoplay = false;*/
  }

  loadScript(url: string){
    const body = <HTMLDivElement> document.body;
    const script = document.createElement('script');
    script.innerHTML = '';
    script.src = url;
    script.async = false;
    script.defer = true;
    script.type = 'text/javascript';
    body.appendChild(script);
  }


  /*public showWebcam = true;
  // latest snapshot
  public webcamImage: WebcamImage = null;
  // webcam snapshot trigger
  private trigger: Subject<void> = new Subject<void>();

  public triggerSnapshot(): void {
    this.trigger.next();
  }
  public toggleWebcam(): void {
    this.showWebcam = !this.showWebcam;
  }
  public handleImage(webcamImage: WebcamImage): void {
    console.info("received webcam image", webcamImage);
    this.webcamImage = webcamImage;
  }
  public get triggerObservable(): Observable<void> {
    return this.trigger.asObservable();
  }*/



  onSubmit(){
    this.account = this.viewInfoComp.childAccount;
    console.log(this.account);
    this.router.navigate(['phonenoVerification/'+this.refParam]);
  }

}
