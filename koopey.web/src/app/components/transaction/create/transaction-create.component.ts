//Angular, Material, Libraries
import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { Router } from "@angular/router";
import { MdDatepickerModule, MdDatepickerIntl } from '@angular/material';
import { Subscription } from 'rxjs/Subscription';
//Services
import { AlertService } from "../../../services/alert.service";
import { AuthService } from "../../../services/auth.service";
import { BitcoinService } from "../../../services/bitcoin.service";
import { ClickService, CurrentComponent, ActionIcon } from "../../../services/click.service";
import { EthereumService } from "../../../services/ethereum.service";
import { TransactionService } from "../../../services/transaction.service";
import { TranslateService } from "ng2-translate";
import { AssetService } from "../../../services/asset.service";
import { UserService } from "../../../services/user.service";
import { WalletService } from "../../../services/wallet.service";
//Helpers
import { TransactionHelper } from "../../../helpers/TransactionHelper";
import { CurrencyHelper } from "../../../helpers/CurrencyHelper";
//Objects
import { Alert } from "../../../models/alert";
import { Bitcoin } from "../../../models/bitcoin";
import { Config } from "../../../config/settings";
import { Ethereum } from "../../../models/ethereum";
import { Asset } from "../../../models/asset";
import { Transaction, TransactionType } from "../../../models/transaction";
import { User, UserType } from "../../../models/user";
import { Wallet, CurrencyType } from "../../../models/wallet";

@Component({
    selector: "transaction-create-component",
    templateUrl: "../../views/transaction-create.html",
    styleUrls: ["../../styles/app-root.css"]
})

//Note* Parameters such as buyer,seller and asset are normally set before TransactionCreateComponent. Quantity is controlled by form view.   

export class TransactionCreateComponent implements OnInit, OnDestroy {

    private clickSubscription: Subscription;   
    private walletSubscription: Subscription;
    private transactionSubscription: Subscription;   
    protected transaction: Transaction = new Transaction();
    private sellerAccount: string = "";  
    protected redirect: boolean = true;
    /*@ViewChild(MdDatepicker ) datepicker: MdDatepicker<Date>;*/

    //calculate share of value
    // private buyerShareValue: number = 0;
    // private sellerShareValue: number = 0;

    constructor(
        protected alertService: AlertService,
        protected authenticateService: AuthService,
        protected bitcoinService: BitcoinService,
        protected ethereumService: EthereumService,
        protected clickService: ClickService,
        protected datePickerService: MdDatepickerIntl,
        protected router: Router,
        protected transactionService: TransactionService,
        protected translateService: TranslateService,
        protected assetService: AssetService,
        protected userService: UserService,
        protected walletService: WalletService/*,private dateAdapter:DateAdapter<Date>*/
    ) {

        //dateAdapter.setLocale('de'); // DD.MM.YYYY
    }

    ngOnInit() {
        this.clickService.createInstance(ActionIcon.CREATE, CurrentComponent.TransactionCreateComponent);
        this.clickSubscription = this.clickService.getTransactionCreateClick().subscribe(() => {
            this.createTransaction();
        });
    }

    ngAfterContentInit() {
        //Read stored transaction, then check auth user is added, then wallets for each user.
        this.transactionSubscription = this.transactionService.getTransaction().subscribe(
            (transaction) => { if (transaction) { this.transaction = transaction; } else { this.transaction = new Transaction() } },
            (error) => { console.log(error); },
            () => { });
    }

    ngAfterViewInit() {
        //AuthUser must be listed in the transaction, add if not already added
        var authUser = this.authenticateService.getLocalUser();
        authUser.type = UserType.Buyer;
        if (!User.contains(this.transaction.users, authUser)) {
            this.transaction.users.push(authUser);
        }
    }

    ngOnDestroy() {
        if (this.clickSubscription) {
            this.clickService.destroyInstance();
            this.clickSubscription.unsubscribe();
        }
        if (this.transactionSubscription) {
            this.transactionSubscription.unsubscribe();
        }
        if (this.walletSubscription) {
            this.walletSubscription.unsubscribe();
        }
    }

    protected readWallets() {
        for (var i = 0; i < this.transaction.users.length; i++) {
            this.walletSubscription = this.walletService.readWallets(this.transaction.users[i]).subscribe(
                (wallets) => {
                    for (var j = 0; j < wallets.length; j++) {
                        //Only copy wallet of selected currency, including authUser
                        if (wallets[j].type == this.transaction.currency) {
                            this.transaction.users[i].wallets.push(wallets[j]);                           
                        }
                    }
                },
                (error) => { console.log(error); },
                () => { }
            );
        }
    }

    public setTransaction(transaction: Transaction) {
        this.transaction = transaction;
    }

    protected convertCurrencySymbol(currency: string): string {
        if (currency != null) {
            return CurrencyHelper.convertCurrencyCodeToSymbol(currency);
        }
    }

    private hasCurrency(currency: string): boolean {
        return Config.transaction_currencies.includes(currency);
    }


    protected onSellerAccountChange($event: any) {
        if (this.transaction.type == 'AuthBuyerToUnAuthSeller') {
            var unAuthUser = new User();
            unAuthUser.id = Config.default_id;
            unAuthUser.type = UserType.Seller;
            var wallet = new Wallet();
            wallet.currency = this.transaction.currency;
            wallet.name = this.sellerAccount;
            if (this.transaction.currency = CurrencyType.Ethereum) { }
        }
    }

    protected onQuantityChange($event: any) {
        this.transaction.totalValue = this.transaction.itemValue * this.transaction.quantity;
    }


    private checkBitcoinBalances(): boolean {
        for (var i = 0; i < this.transaction.users.length; i++) {
            var buyerWallet = Wallet.readBitcoin(this.transaction.users[i].wallets);
            if (buyerWallet) {
                var bitcoinTransaction: Bitcoin = new Bitcoin();
                bitcoinTransaction.address = buyerWallet.name;
                this.bitcoinService.readBalance(bitcoinTransaction).subscribe(
                    (bitcoin) => {
                        //Use same bitcoin object to collect response
                        bitcoinTransaction = bitcoin;
                    },
                    (error) => { this.alertService.error(<any>error) },
                    () => {
                        if (bitcoinTransaction.amount < TransactionHelper.BuyerShareValuePlusMargin(this.transaction)) {
                            //not enough funds
                            this.alertService.success("ERROR_NOT_ENOUGH_FUNDS");
                            return false;
                        } else if (this.transaction.users.length == (i - 1)) {
                            //sufficient funds
                            return true;
                        }
                    });
            } else {
                return false;
            }
        }
    }

    private checkEthereumBalances(): boolean {
        for (var i = 0; i < this.transaction.users.length; i++) {
            var buyerWallet = Wallet.readBitcoin(this.transaction.users[i].wallets);
            if (buyerWallet) {
                var ethereumTransaction: Ethereum = new Ethereum();
                ethereumTransaction.address = buyerWallet.name;
                this.ethereumService.readBalance(ethereumTransaction).subscribe(
                    (ethereum) => { ethereumTransaction = ethereum; },
                    (error) => { this.alertService.error(<any>error) },
                    () => {
                        if (ethereumTransaction.balance < TransactionHelper.BuyerShareValuePlusMargin(this.transaction)) {
                            //not enough funds
                            this.alertService.success("ERROR_NOT_ENOUGH_FUNDS");
                            return false;
                        } else if (this.transaction.users.length == (i - 1)) {
                            //sufficient funds
                            return true;
                        }
                    });
            } else {
                return false;
            }
        }
    }

    private checkLocalBalances(): boolean {
        for (var i = 0; i < this.transaction.users.length; i++) {
            var buyerWallet = Wallet.readLocal(this.transaction.users[i].wallets);
            if (buyerWallet) {
                if ((buyerWallet.value >= TransactionHelper.BuyerShareValue(this.transaction))
                ) {
                    return true;
                } else {
                    this.alertService.success("ERROR_NOT_ENOUGH_FUNDS");
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    /*private checkQuantity(): boolean {
        if (Asset.isProduct(this.transaction.asset)) {
            if (this.transaction.asset.quantity <= this.transaction.quantity) {
                return true;
            } else {
                this.alertService.error("ERROR_TOO_MUCH_QUANTITY");
                return false;
            }
        } else {
            return true;
        }
    }*/

    private createTransaction() {
        //readWallets

        this.readWallets();
        console.log(this.transaction);
        if (this.transaction && !Transaction.isEmpty(this.transaction)) {
            if ((this.transaction.currency == CurrencyType.Bitcoin) && this.checkBitcoinBalances()) {
                this.createTransactionBitcoin()
            } else if ((this.transaction.currency == CurrencyType.Ethereum) && this.checkEthereumBalances()) {
                this.createTransactionEthereum()
            } else if ((this.transaction.currency == CurrencyType.Local) && this.checkLocalBalances()) {
                this.createTransactionLocal()
            } else if ((this.transaction.currency == CurrencyType.Euro)
                || (this.transaction.currency == CurrencyType.BritishPound)
                || (this.transaction.currency == CurrencyType.UnitedStatesDollar)
                || (this.transaction.currency == CurrencyType.SouthAfricanRand)) {
                this.createTransactionAuditTrail();
                // this.createTransactionFiat();
            }
        } else {
            this.alertService.error("ERROR_FORM_NOT_VALID");
        }

    }

    private createTransactionAuditTrail() {
        //Create transaction
        console.log("createTransactionTrail()");
        this.transactionService.create(this.transaction).subscribe(
            () => { },
            (error) => { this.alertService.error(<any>error) },
            () => {
                //update asset quantity if not null  
                if (!Asset.isEmpty(this.transaction.asset) && Asset.isProduct(this.transaction.asset)) {
                    this.decrementQuantity();
                } else {
                    if (this.redirect) {
                        console.log("createTransaction() route");
                        this.router.navigate(["/transaction/read/list"])
                    } else {
                        console.log("createTransaction() no route");
                        this.alertService.success("INFO_COMPLETE");
                    }
                }
            }
        );
    }

    //https://bitcoin.org/en/developer-examples#simple-raw-transaction
    // var parameters = {
    //  "txid": "263c018582731ff54dc72c7d67e858c002ae298835501d\80200f05753de0edf0",
    //  "vout": 0,
    //  "address": "muhtvdmsnbQEPFuEmxcChX58fGvXaaUoVt",
    //  "scriptPubKey": "76a9149ba386253ea698158b6d34802bb9b550\f5ce36dd88ac",
    //  "amount": 40.00000000,
    //  "confirmations": 1,
    //  "spendable": true,
    //  "solvable": true
    //}
    private createTransactionBitcoin() {
        //create all transactions to invoice account from buyer
        for (var i = 0; i < this.transaction.users.length; i++) {
            if (this.transaction.users[i].type == "buyer") {
                var bitcoinTransaction = new Bitcoin();
                bitcoinTransaction.fromaddress = Wallet.readBitcoin(this.transaction.users[i].wallets).name;
                bitcoinTransaction.amount = TransactionHelper.BuyerShareValuePlusMargin(this.transaction);
                this.bitcoinService.createSystemInvoice(bitcoinTransaction).subscribe(
                    (alert: Alert) => {
                        this.showTransactionCompleteAlert(alert);
                    },
                    (error) => { this.alertService.error(<any>error) },
                    () => { this.createTransactionAuditTrail(); }
                );
            } else if (this.transaction.users[i].type == "seller") {
                //seller only gets paid on the request of buyer
            }
        }
    }

    private createTransactionEthereum() {
        //create all transactions to invoice account from buyer
        for (var i = 0; i < this.transaction.users.length; i++) {
            if (this.transaction.users[i].type == "buyer") {
                var ethereumTransaction = new Ethereum()
                ethereumTransaction.from = Wallet.readEthereum(this.transaction.users[i].wallets).name;
                ethereumTransaction.value = this.transaction.totalValue.toString(16);
                this.ethereumService.createSystemInvoice(ethereumTransaction).subscribe(
                    (alert: Alert) => {
                        this.showTransactionCompleteAlert(alert);
                    },
                    (error) => { this.alertService.error(<any>error) },
                    () => { this.createTransactionAuditTrail(); }
                );
            } else if (this.transaction.users[i].type == "seller") {
                //seller only gets paid on the request of buyer
            }
        }
    }

    private createTransactionLocal() {
       var cost =  TransactionHelper.BuyerShareValue(this.transaction);
        //create all transactions
        for (var j = 0; j < this.transaction.users.length; j++) {
            if (this.transaction.users[j].type == "buyer") {
                //update buyer
                this.walletService.updateWalletByAddition(this.transaction.users[j].id, -cost).subscribe(
                    () => { },
                    (error) => { this.alertService.error(<any>error) },
                    () => { }
                );
            } else if (this.transaction.users[j].type == "seller") {
                //update seller
                this.walletService.updateWalletByAddition(this.transaction.users[j].id, cost).subscribe(
                    () => { },
                    (error) => { this.alertService.error(<any>error) },
                    () => { }
                );
            }
        }
        //createTransaction audit trail and update quantity
        this.createTransactionAuditTrail();

    }

    private decrementQuantity() {
        this.assetService.updateQuantity(this.transaction.asset, -1 * this.transaction.quantity).subscribe(
            () => { console.log("createTransaction() updateQuantity"); },
            (error) => { this.alertService.error("ERROR"); console.log(error); },
            () => {
                if (this.redirect) {
                    console.log("createTransaction() updateQuantity route");
                    this.router.navigate(["/transaction/read/list"])
                } else {
                    console.log("createTransaction() updateQuantity no route");
                    this.alertService.success("INFO_COMPLETE");
                }
            });
    }

    private showTransactionCompleteAlert(alert: Alert) {
        if (Alert.isSuccess(alert)) {
            this.alertService.success("INFO_COMPLETE");
        } else {
            this.alertService.error(alert.message)
        }
    }
}