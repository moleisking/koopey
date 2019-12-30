import { Component } from '@angular/core';
import { MdDialog, MdDialogRef } from '@angular/material';
import { User } from "../models/user";

@Component({
    selector: 'mobile-dialog',
    templateUrl: '../../views/mobile-dialog.html',
})

export class MobileDialogComponent {

    private mobile: string;

    constructor(public dialogRef: MdDialogRef<MobileDialogComponent>) {
        // this.mobile = user.mobile;
    }

    public setMobile(mobile: string) { this.mobile = mobile; }
}