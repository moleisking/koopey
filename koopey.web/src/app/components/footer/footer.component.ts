import { ChangeDetectionStrategy, Component } from "@angular/core";

@Component({
    changeDetection: ChangeDetectionStrategy.OnPush  ,
  selector: "footer",
    standalone: false,
  styleUrls: ["footer.css"],
  templateUrl: "footer.html",
})
export class FooterComponent {}
