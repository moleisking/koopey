//Core
import { Component, ElementRef, Input, OnInit, ViewChild } from "@angular/core";
import { MaterialModule, MdInputModule, MdTextareaAutosize } from "@angular/material";
import { Observable, Subscription } from "rxjs/Rx";
//Services
import { AlertService } from "../services/alert.service";
import { BitcoinService } from "../services/bitcoin.service";
import { EthereumService } from "../services/ethereum.service";
import { MessageService } from "../services/message.service";
import { AssetService } from "../services/asset.service";
import { TransactionService } from "../services/transaction.service";
import { TranslateService } from "ng2-translate";
import { UserService } from "../services/user.service";
//Objects
import { Config } from "../config/settings";
import { Bitcoin } from "../models/bitcoin";
import { Ethereum } from "../models/ethereum";
import { Message } from "../models/message";
import { User } from "../models/user";
import { Asset } from "../models/asset";

@Component({
    selector: 'report-component',
    templateUrl: '../../views/report.html'
})

export class ReportComponent implements OnInit {
   
    private assetSubscription: Subscription;
    private bitcoinSubscription: Subscription;
    private ethereumSubscription: Subscription;
    private messageSubscription: Subscription;   
    private transactionSubscription: Subscription;
    private userSubscription: Subscription;

    public bitcoinState: Boolean = false;
    public ethereumState: Boolean = false;
    public messageCount: Number = 0;
    public assetCount: Number = 0;
    public transactionCount: Number = 0;   
    public userCount: Number = 0;

    constructor(
        private alertService: AlertService,
        private assetService: AssetService,  
        private bitcoinService: BitcoinService,    
        private ethereumService: EthereumService, 
        private messageService: MessageService,               
        private transactionService: TransactionService,
        private translateService: TranslateService,
        private userService: UserService) {
    }

    ngOnInit() { 
        this.assetService.readCount().subscribe(
            (count) => { this.assetCount = count; },
            (error) => { this.alertService.error("ERROR_EMPTY"); },
            () => {  }
        );
        this.bitcoinService.readBlockchainInfo().subscribe(
            (bitcoin: Bitcoin) => { if (bitcoin.chain.length > 0) { this.bitcoinState = true;} else {this.bitcoinState =false; } },
            (error) => { this.alertService.error("ERROR_EMPTY"); console.log(error) },
            () => {  }
        );
        this.ethereumService.readBlockNumber().subscribe(
            (ethereum : Ethereum) => { if (ethereum.block> 0) {this.ethereumState = true; } else {this.ethereumState = true;}  },
            (error) => { this.alertService.error("ERROR_EMPTY"); },
            () => {  }
        );
        this.messageService.readCount().subscribe(
            (count) => { this.messageCount = count; },
            (error) => { this.alertService.error("ERROR_EMPTY"); },
            () => {  }
        );
        this.userService.readCount().subscribe(
            (count) => { this.userCount = count; },
            (error) => { this.alertService.error("ERROR_EMPTY"); },
            () => {  }
        );     
        this.transactionService.readCount().subscribe(
            (count) => { if (count) { this.transactionCount = count; } },
            (error) => { this.alertService.error("ERROR_EMPTY"); },
            () => {  }
        );
    }

    ngOnDestroy() {
        if (this.assetSubscription) {            
            this.assetSubscription.unsubscribe();
        }
        if (this.bitcoinSubscription) {            
            this.bitcoinSubscription.unsubscribe();
        }
        if (this.ethereumSubscription) {            
            this.ethereumSubscription.unsubscribe();
        }
        if (this.messageSubscription) {            
            this.messageSubscription.unsubscribe();
        }      
        if (this.transactionSubscription) {            
            this.transactionSubscription.unsubscribe();
        }
        if (this.userSubscription) {            
            this.userSubscription.unsubscribe();
        }
    }  
}