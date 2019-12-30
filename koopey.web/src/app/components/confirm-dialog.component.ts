import { Component, OnInit, ElementRef, ViewChild } from "@angular/core";
import { MaterialModule, MdInputModule, MdTextareaAutosize, MdDialog, MdDialogRef } from "@angular/material"

@Component({
    selector: 'file-upload-dialog',
    templateUrl: '../../views/confirm-dialog.html',
})
    
export class ConfirmDialogComponent {
    constructor(public dialogRef: MdDialogRef<ConfirmDialogComponent>) { }
}