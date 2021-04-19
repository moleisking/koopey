const SHA256 = require("crypto-js/sha256");
import { UUID } from 'angular2-uuid';
import { Bitcoin } from "../models/bitcoin";
import { Ethereum } from "../models/ethereum";

export enum CurrencyType {
    Bitcoin = 'btc',
    SwissFranc = 'chf',
    Renminbi = 'cny',
    Ethereum = 'eth',
    Euro = 'eur',
    BritishPound = 'gbp',   
    JapaneseYen = 'jpy',
    UnitedStatesDollar = 'usd',
    SouthAfricanRand = 'zar',
    Local = "tok"
};

export class Wallet {

    public id: string = UUID.UUID();
    public value: number = 0;
    public userId: string = '';
    public currency: string = '';
    public name: string = '';
    public pubKey: string = '';
    public prvKey: string = '';
    public type: string = '';
    public hash: string = '';
    public bitcoin: Bitcoin = new Bitcoin();
    public ethereum: Ethereum = new Ethereum();
    public createTimeStamp: number = Date.now();
    public readTimeStamp: number = 0;
    public updateTimeStamp: number = 0;
    public deleteTimeStamp: number = 0;

    public static isEmpty(wallet: Wallet): Boolean {
        if (wallet && wallet.currency && wallet.type &&
            wallet.currency.match('btc|eth|eur|gbp|usd|zar') &&
            wallet.type.match('primary|secondary|iban')) {
            return false;
        } else {
            return true;
        }
    }

    public static isEmptyBitcoin(wallet: Wallet): Boolean {
        if (wallet && wallet.currency && wallet.type &&
            wallet.name && (wallet.name.length > 0) &&
            wallet.currency.match('btc') &&
            wallet.type.match('primary|secondary|iban')) {
            return false;
        } else {
            return true;
        }
    }

    public static isEmptyEthereum(wallet: Wallet): Boolean {
        if (wallet && wallet.currency && wallet.type &&
            wallet.name && (wallet.name.length > 0) &&
            wallet.currency.match('eth') &&
            wallet.type.match('primary|secondary|iban')) {
            return false;
        } else {
            return true;
        }
    }

    public static contains(wallets: Array<Wallet>, wallet: Wallet): Boolean  {
        if (wallets && wallets.length > 0 && wallet && wallet.id && wallet.type) {
            for (var i = 0; i < wallets.length; i++) {
                if ((wallets[i]) && (wallets[i].id === wallet.id ||
                    wallets[i].type === wallet.type)) {
                    return true;
                } else if (i === wallets.length - 1) {
                    return false;
                }
            }
        } else {
            return false;
        }  
        return false;     
    }

    public static containsBitcoin(wallets: Array<Wallet>): boolean {
        return Wallet.containsCurrency(wallets, "btc");
    }

    public static containsEthereum(wallets: Array<Wallet>): boolean {
        return Wallet.containsCurrency(wallets, "eth");
    }

    public static containsPrimary(wallets: Array<Wallet>): boolean {
        return Wallet.containsType(wallets, "primary");
    }

    public static containsIban(wallets: Array<Wallet>): boolean {
        return Wallet.containsType(wallets, "iban");
    }

    public static containsLocal(wallets: Array<Wallet>): boolean {
        return Wallet.containsCurrency(wallets, "tok");
    }

    public static containsCurrency(wallets: Array<Wallet>, currency: string): boolean {
        if (wallets && wallets.length > 0 && currency) {
            for (var i = 0; i < wallets.length; i++) {
                if (wallets[i] &&
                    wallets[i].currency === currency) {
                    return true;
                } else if (i === wallets.length - 1) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    public static containsType(wallets: Array<Wallet>, type: string): boolean {
        if (wallets && wallets.length > 0 && type) {
            for (var i = 0; i < wallets.length; i++) {
                if (wallets[i] &&
                    wallets[i].type === type) {
                    return true;
                } else if (i === wallets.length - 1) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    public static create(wallets: Array<Wallet>, wallet: Wallet): Array<Wallet> {
        if (!Wallet.contains(wallets, wallet)) {
            wallets.push(wallet);
            return wallets;
        } else {
            return wallets;
        }
    }

    public static read(wallets: Array<Wallet>, wallet: Wallet): Wallet {
        if (wallets && wallets.length > 0) {
            for (var i = 0; i < wallets.length; i++) {
                if (wallets[i] &&
                    wallets[i].id == wallet.id &&
                    wallets[i].type == wallet.type) {
                    return wallets[i];
                }
            }
        }
      return new Wallet();
    }

    public static readByCurrency(wallets: Array<Wallet>, currency: string): Wallet  {
        if (wallets && wallets.length > 0) {
            for (var i = 0; i < wallets.length; i++) {
                if (wallets[i] && wallets[i].currency &&
                    (wallets[i].currency === currency)) {
                    return wallets[i];
                }
            }
        }
        return new Wallet();
    }    

    public static readBitcoin(wallets: Array<Wallet>): Wallet {
        return Wallet.readByCurrency(wallets, CurrencyType.Bitcoin);
    }

    public static readEthereum(wallets: Wallet[]): Wallet  { 
        return Wallet.readByCurrency(wallets, CurrencyType.Ethereum);
    }

    public static readUnitedStatesDollar(wallets: Array<Wallet>): Wallet {
        return Wallet.readByCurrency(wallets, CurrencyType.UnitedStatesDollar);
    }

    public static readLocal(wallets: Array<Wallet>): Wallet {
        return Wallet.readByCurrency(wallets, CurrencyType.Local);
    }

    public static readBritishPound(wallets: Array<Wallet>): Wallet  {
        return Wallet.readByCurrency(wallets, CurrencyType.BritishPound);
    }

    public static readSouthAfricanRand(wallets: Array<Wallet>): Wallet {
        return Wallet.readByCurrency(wallets, CurrencyType.SouthAfricanRand);
    }

    public static readByType(wallets: Array<Wallet>, type: string): Wallet  {
        if (wallets && wallets.length > 0) {
            for (var i = 0; i <= wallets.length; i++) {
                if (wallets[i] && (wallets[i].type === type)) {
                    return wallets[i];
                }
            }
        }
        return new Wallet();
    }

    public static update(wallets: Array<Wallet>, wallet: Wallet): Array<Wallet>  {
        if (wallets && wallets.length > 0) {
            for (var i = 0; i < wallets.length; i++) {
                if (wallets[i] &&
                    wallets[i].id == wallet.id) {
                    wallets[i] = wallet;
                    return wallets;
                }
            }
        }
        return new Array<Wallet>();
    }

    public static delete(wallets: Array<Wallet>, wallet: Wallet): Array<Wallet>  {
        if (wallets && wallets.length > 0) {
            for (var i = 0; i < wallets.length; i++) {
                if (wallets[i] &&
                    wallets[i].id == wallet.id) {
                    wallets.splice(i, 1);
                    return wallets;
                }
            }
        }
        return new Array<Wallet>();
    }
}