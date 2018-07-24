import { Component, OnInit } from '@angular/core';
import {WebcamImage} from "../webcam/domain/webcam-images";
import {Subject} from "rxjs/Subject";
import {Observable} from "rxjs/Observable";
import {Router} from "@angular/router";

@Component({
  selector: 'app-videoregistration',
  templateUrl: './videoregistration.component.html',
  styleUrls: ['./videoregistration.component.css']
})
export class VideoregistrationComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit() {
  }

  public showWebcam = true;

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
  }

  registerstep3(){
    this.router.navigate(['/registerstep3']);
  }

}
