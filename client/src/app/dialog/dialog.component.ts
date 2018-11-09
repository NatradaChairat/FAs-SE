import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {FormControl} from "@angular/forms";

export interface DialogData {
  type: string;
  title: string;
  detail: string;
  isWarningMessage: boolean;
  isOptionMessage: boolean;
}
@Component({
  selector: 'app-emailregistration-diolog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent {

  reasons: string[] = ['Face is blur, not matched with Student card', 'Student ID not matched with Student card'];

  constructor(
    private dialogRef: MatDialogRef<DialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) { }

  onClick(): void {
    this.dialogRef.close();

  }

}
