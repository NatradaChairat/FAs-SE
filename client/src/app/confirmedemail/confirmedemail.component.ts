import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-confirmedemail',
  templateUrl: './confirmedemail.component.html',
  styleUrls: ['./confirmedemail.component.css']
})
export class ConfirmedemailComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }
  registerstep2(){
    console.log("go to register step2");
  }
}
