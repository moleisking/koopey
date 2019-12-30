export class CurrencyHelper {

    public static convertCurrencyCodeToSymbol(currency: string): string {
        if (currency != null) {
            if (currency.toLowerCase() == 'btc') {
                return '฿';
            } else if (currency.toLowerCase() == 'eth') {
                return 'Ξ';
            } else if (currency.toLowerCase() == 'eur') {
                return '€';          
            } else if (currency.toLowerCase() == 'gbp') {
                return '£';
            } else if (currency.toLowerCase() == 'usd') {
                return '$';
            } else if (currency.toLowerCase() == 'tok') {
                return 'T';
            } else if (currency.toLowerCase() == 'zar') {
                return 'R';
            } else {
                return 'NA';
            }
        }
    }

    public static convertCurrencySymbolToCode(symbol: string): string {
        if (symbol != null) {
            if (symbol.toLowerCase() == '฿') {
                return 'btc';
            } else if (symbol.toLowerCase() == '€') {
                return 'eur';
            } else if (symbol.toLowerCase() == 'Ξ') {
                return 'eth';
            } else if (symbol.toLowerCase() == '£') {
                return 'gbp';
            } else if (symbol.toLowerCase() == '$') {
                return 'usd';
            } else if (symbol.toLowerCase() == 'T') {
                return 'tok';
            } else if (symbol == 'R') {
                return 'zar';
            } else {
                return 'NA';
            }
        }
    }


}
