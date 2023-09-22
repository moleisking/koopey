package com.koopey.model;

import com.koopey.model.base.BaseCollection;

public class Wallets extends BaseCollection<Wallet> {

    public static final String WALLETS_FILE_NAME = "wallets.dat";

    public Wallet getTokoWallet()
    {
        return this.get("tok");
    }

    public Wallet getBitcoinWallet()
    {
        return this.get("btc");
    }

    public Wallet getEthereumWallet()
    {
        return this.get("eth");
    }

    public Wallet getIBANWallet()
    {
        for(int x = 0; x < this.size(); x++ ){
            if ((this.get(x).currency.equals("usd")
                    || this.get(x).currency.equals("gbp")
                    || this.get(x).currency.equals("eur")
                    || this.get(x).currency.equals("rsa"))
                    && this.get(x).getType().equals("primary")){
                return this.get(x);
            }
        }
        return null;
    }

    public Wallet get(String currency)
    {
        for (int i =0; i < this.size();i++)
        {
            if ( this.get(i).currency.equals(currency) && this.get(i).getType().equals("primary"))
            {
                return this.get(i);
            }
        }
        return null;
    }
    public Wallets getWalletsExceptLocal() {
        Wallets w = new Wallets();
        for(int x = 0; x < this.size(); x++ ){
            if(!this.get(x).currency.equals("tok")) {
                w.add(this.get(x));
            }
        }
        return w;
    }
   /* public ArrayList<Wallet> getArrayList()    {
        return (ArrayList)this.wallets;//new ArrayList<Tag>( this.tags.toArray());
    }

    public int size()
    {
        return this.wallets.size();
    }*/


}
