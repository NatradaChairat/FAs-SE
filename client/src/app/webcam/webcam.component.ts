import {AfterViewInit, Component, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild} from '@angular/core';
import {WebcamInitError} from "./domain/webcam-init-error";
import {WebcamImage} from "./domain/webcam-images";
import {Observable} from "rxjs/Observable";
import {Subscription} from "rxjs/Subscription";

@Component({
  selector: 'app-webcam',
  templateUrl: './webcam.component.html',
  styleUrls: ['./webcam.component.css']
})
export class WebcamComponent implements OnInit,AfterViewInit,OnDestroy {


  @Input() public width: number = 640;
  @Input() public height: number = 480;
  private _trigger:Observable<void>;
  private triggerSubscription: Subscription;

  private mediaStream: MediaStream = null;

  @Output() public imageCapture: EventEmitter<WebcamImage> = new EventEmitter<WebcamImage>();
  @Output() public initError: EventEmitter<WebcamInitError> = new EventEmitter<WebcamInitError>();
  @Output() public imageClick: EventEmitter<void> = new EventEmitter<void>();

  @ViewChild('video') private video: any;
  @ViewChild('canvas') private canvas: any;

  constructor() { }

  ngOnInit() {
  }


  ngAfterViewInit(): void {
    this.initWebcam();
  }

  ngOnDestroy(): void {
    this.stopMediaTracks();
    this.unsubscribeFromSubscriptions();
  }


  @Input()

  public set trigger(trigger: Observable<void>) {
    if (this.triggerSubscription) {
      this.triggerSubscription.unsubscribe();
    }
    this._trigger = trigger;
    this.triggerSubscription = this._trigger.subscribe(() => {
      this.takeSnapshot();
    });
  }

  public takeSnapshot(): void {
    let _video = this.video.nativeElement;
    let dimensions = {width: this.width, height: this.height};
    if (_video.videoWidth) {
      dimensions.width = _video.videoWidth;
      dimensions.height = _video.videoHeight;
    }

    let _canvas = this.canvas.nativeElement;
    _canvas.width = dimensions.width;
    _canvas.height = dimensions.height;
    _canvas.getContext('2d').drawImage(this.video.nativeElement, 0, 0);

    let mimeType: string = "image/jpeg";
    let dataUrl: string = _canvas.toDataURL(mimeType);
    this.imageCapture.next(new WebcamImage(dataUrl, mimeType));
  }

  private initWebcam() {
    let _video = this.video.nativeElement;
    if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
      navigator.mediaDevices.getUserMedia(<MediaStreamConstraints>{video: {facingMode: "environment"}})
        .then((stream: MediaStream) => {
          this.mediaStream = stream;
          _video.srcObject = stream;
          _video.play();
        })

        .catch((err: MediaStreamError) => {
          this.initError.next(<WebcamInitError>{message: err.message, mediaStreamError: err});
          console.warn("Error initializing webcam", err);
        });
    } else {
      this.initError.next(<WebcamInitError> {message: "Cannot read UserMedia from MediaDevices."});
    }
  }


  private stopMediaTracks() {
    if (this.mediaStream && this.mediaStream.getTracks) {
      // getTracks() returns all media tracks (video+audio)
      this.mediaStream.getTracks()
        .forEach((track: MediaStreamTrack) => track.stop());
    }
  }


  private unsubscribeFromSubscriptions() {
    if (this.triggerSubscription) {
      this.triggerSubscription.unsubscribe();
    }
  }

}
