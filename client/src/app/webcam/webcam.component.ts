import {
  AfterViewInit,
  Component,
  EventEmitter,
  Injectable,
  Input,
  OnDestroy,
  OnInit,
  Output,
  ViewChild
} from '@angular/core';
import {WebcamInitError} from "./domain/webcam-init-error";
import {WebcamImage} from "./domain/webcam-images";
import {Observable} from "rxjs/Observable";
import {Router} from "@angular/router"
import {Subscription} from "rxjs/Subscription";
import {Subject} from '../../../node_modules/rxjs';
import {WebcamUtil} from '../../../node_modules/ngx-webcam';
import {FaceLoginComponent} from "../face-login/face-login.component";

@Component({
  selector: 'app-webcam',
  templateUrl: './webcam.component.html',
  styleUrls: ['./webcam.component.css']
})

export class WebcamComponent implements OnInit {

  constructor(private router: Router) {}

  @Input() isSnapshot = false;

// toggle webcam on/off
  public showWebcam = true;
  public multipleWebcamsAvailable = false;
  public deviceId: string;
  public errors: WebcamInitError[] = [];

  // latest snapshot
  public showWebCamImage = false;
  @Input() webcamImage: WebcamImage = null;

  // webcam snapshot trigger
  private trigger: Subject<void> = new Subject<void>();
  // switch to next / previous / specific webcam; true/false: forward/backwards, string: deviceId
  private nextWebcam: Subject<boolean|string> = new Subject<boolean|string>();


  public ngOnInit(): void {
    WebcamUtil.getAvailableVideoInputs()
      .then((mediaDevices: MediaDeviceInfo[]) => {
        this.multipleWebcamsAvailable = mediaDevices && mediaDevices.length > 1;
      });
  }

   triggerSnapshot(): void {

      this.trigger.next();
      this.showWebcam = false;
      this.showWebCamImage = true;
      this.isSnapshot = true;

  }

  retakePhoto() {
    if(window.confirm("Do you want to retake a photo?")){
      this.showWebcam = true;
      this.showWebCamImage = false;
    } else {

    }
  }

  public handleInitError(error: WebcamInitError): void {
    this.errors.push(error);
  }

  public showNextWebcam(directionOrDeviceId: boolean|string): void {
    // true => move forward through devices
    // false => move backwards through devices
    // string => move to device with given deviceId
    this.nextWebcam.next(directionOrDeviceId);
  }

  public handleImage(webcamImage: WebcamImage): void {
    webcamImage.imageAsBase64;
    console.info('received webcam image', webcamImage);
    this.webcamImage = webcamImage;
  }

  public cameraWasSwitched(deviceId: string): void {
    console.log('active device: ' + deviceId);
    this.deviceId = deviceId;
  }

  public get triggerObservable(): Observable<void> {
    return this.trigger.asObservable();
  }

  public get nextWebcamObservable(): Observable<boolean|string> {
    return this.nextWebcam.asObservable();
  }

}
