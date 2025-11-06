import { ChangeDetectionStrategy, Component, Input } from "@angular/core";
import { MatIconModule } from "@angular/material/icon";

@Component({
    changeDetection: ChangeDetectionStrategy.OnPush  ,
    imports : [MatIconModule],
  selector: "starbox",
    standalone: true,
  styleUrls: ["starbox.css"],
  templateUrl: "starbox.html",
})
export class StarboxComponent {
  @Input() average: number = 0;

  constructor() {}
}
