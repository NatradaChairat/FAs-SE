import {Component, Inject, OnInit} from '@angular/core';
import {DialogData, EmailRegistrationComponent} from "../emailregistration/emailregistration.component";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-emailregistration-diolog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent {

  constructor(
    private dialogRef: MatDialogRef<DialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {
    //dialogRef.disableClose =true;
  }

  onClick(): void {
    this.dialogRef.close();

  }

}
