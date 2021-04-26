import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
  name: "currency-code-to-symbol",
})
export class CurrencyCodeToSymbolPipe implements PipeTransform {
  transform(code: string): string {
    return this.convertCurrencyCodeToSymbol(code);
  }

  private convertCurrencyCodeToSymbol(currency: string): string {
    if (currency.toLowerCase() == "btc") {
      return "฿";
    } else if (currency.toLowerCase() == "eth") {
      return "Ξ";
    } else if (currency.toLowerCase() == "eur") {
      return "€";
    } else if (currency.toLowerCase() == "gbp") {
      return "£";
    } else if (currency.toLowerCase() == "usd") {
      return "$";
    } else if (currency.toLowerCase() == "zar") {
      return "R";
    } else {
      return "NA";
    }
  }
}
