import {Component, Inject, OnInit} from '@angular/core';
import {DialogData, EmailRegistrationComponent} from "../emailregistration/emailregistration.component";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-emailregistration-diolog',
  templateUrl: './emailregistration-dialog.component.html',
  styleUrls: ['./emailregistration-dialog.component.css']
})
export class EmailregistrationDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<EmailRegistrationComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

  onClick(): void {
    this.dialogRef.close();
  }

}
