import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
  name: "codetosymbol",
})
export class CodeToSymbolPipe implements PipeTransform {
  transform(code: string): string {
    return this.convertCodeToSymbol(code);
  }

  private convertCodeToSymbol(currency: string): string {
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
