import { ChangeDetectionStrategy, Component } from "@angular/core";
import { MatExpansionModule } from "@angular/material/expansion";
import { MatIconModule } from "@angular/material/icon";
import { AssetListComponent } from "../list/asset-list.component";
import { AssetFilterComponent } from "../filter/asset-filter.component";

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [AssetFilterComponent, AssetListComponent, MatExpansionModule, MatIconModule],
  selector: "asset-search",
  standalone: true,
  styleUrls: ["asset-search.css"],
  templateUrl: "asset-search.html",
})
export class AssetSearchComponent { }