import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {DialogComponent, DialogData} from "../dialog/dialog.component";



@Component({
  selector: 'app-account-detail-dialog',
  templateUrl: './account-detail-dialog.component.html',
  styleUrls: ['./account-detail-dialog.component.css']
})
export class AccountDetailDialogComponent {

  constructor(
    private dialogRef: MatDialogRef<DialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) { }

  onClick(): void {
    this.dialogRef.close();
  }

}
