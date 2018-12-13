import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {IntermediaryService} from "../service/intermediary.service";
import {Account} from "../model/Account.model";

@Component({
  selector: 'app-face-login-success',
  templateUrl: './face-login-success.component.html',
  styleUrls: ['./face-login-success.component.css']
})
export class FaceLoginSuccessComponent implements OnInit {

  showConfidence: number;
  showStudentId: string;

  value: number;
  mode = 'determinate';
  color = 'primary';
  bufferValue = 75;

  constructor(private intermediaryService: IntermediaryService) {
  }

  ngOnInit() {
    this.showConfidence = this.intermediaryService.getConfidence();
    this.showStudentId = this.intermediaryService.getStudentId();
    this.value = this.showConfidence * 100;
  }


}
