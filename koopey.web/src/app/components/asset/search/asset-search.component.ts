import { ChangeDetectionStrategy, Component } from "@angular/core";

@Component({
    changeDetection: ChangeDetectionStrategy.OnPush  ,
  selector: "asset-search",
    standalone: false,
  styleUrls: ["asset-search.css"],
  templateUrl: "asset-search.html",
})
export class AssetSearchComponent { }