import { ChangeDetectionStrategy, Component } from "@angular/core";
import { MatExpansionModule } from "@angular/material/expansion";
import { TransactionFilterComponent } from "../filter/transaction-filter.component";
import { TransactionTableComponent } from "../table/transaction-table.component";
import { MatIconModule } from "@angular/material/icon";

@Component({
    changeDetection: ChangeDetectionStrategy.OnPush  ,
    imports: [MatExpansionModule, MatIconModule, TransactionFilterComponent, TransactionTableComponent],
    standalone: true,
    selector: "transaction-search",
    styleUrls: ["transaction-search.css"],
    templateUrl: "transaction-search.html",
  })
  export class TransactionSearchComponent {}