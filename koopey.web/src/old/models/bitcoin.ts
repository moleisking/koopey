export class Bitcoin {
    public account: string = "";
    public address: string = "";
    public amount: number = 0;
    public asm: string = "";
    public balance: number;
    public bestblockhash: string = "";
    public bip9_softforks: Array<any>;
    public blocks: number = 0;
    public chain: string = "";
    public chainwork: string; 
    public complete: Boolean = false;
    public connections: number
    public confirmations: number = 0;  
    public difficulty: number = 0;
    public fromaddress: string = "";
    public fromaccount: string = "";
    public hash: string = "";
    public headers: number = 0;
    public hex: string = "";
    public iscompressed: Boolean = false;
    public isvalid: Boolean = false;
    public ismine: Boolean = false;
    public iswatchonly: Boolean = false;
    public isscript: Boolean = false;
    public keypoololdest: number;
    public keypoolsize: number;
    public localaddresses: Array<any>
    public localrelay: boolean
    public localservices: string   
    public mediantime: number = 0;
    public networks: Array<any        >
    public type: string = "";
    public txid: string = "";
    public protocolversion : number
    public pruned: boolean = false;
    public pruneheight: number = 0; 
    public pubkey: string = "";
    public relayfee: number
    public scriptPubKey: string = "";
    public softforks: Array<any>;   
    public timeoffset: number
    public timestamp: number = 0;  
    public toaddress: string = "";
    public txcount: number;
    public spendable: Boolean = false;
    public solvable: Boolean = false;
    public subversion: string;  
    public unlocked_until: number;
    public verificationprogress: number = 0;
    public version: number = 0;
    public vout: number = 0;
    public warnings: string
    public walletversion: number   
}

