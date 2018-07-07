import {Component, Inject, OnInit} from '@angular/core';
import {DialogData, EmailRegistrationComponent} from "../emailregistration/emailregistration.component";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-emailregistration-diolog',
  templateUrl: './emailregistration-diolog.component.html',
  styleUrls: ['./emailregistration-diolog.component.css']
})
export class EmailRegistrationDiologComponent {

  constructor(
    public dialogRef: MatDialogRef<EmailRegistrationComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
