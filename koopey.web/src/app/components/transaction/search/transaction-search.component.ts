import { ChangeDetectionStrategy, Component } from "@angular/core";

@Component({
    changeDetection: ChangeDetectionStrategy.OnPush  ,
    standalone: false,
    selector: "transaction-search",
    styleUrls: ["transaction-search.css"],
    templateUrl: "transaction-search.html",
  })
  export class TransactionSearchComponent {}