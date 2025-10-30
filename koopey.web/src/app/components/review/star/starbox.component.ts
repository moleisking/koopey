import { ChangeDetectionStrategy, Component, Input } from "@angular/core";

@Component({
    changeDetection: ChangeDetectionStrategy.OnPush  ,
  selector: "starbox",
    standalone: false,
  styleUrls: ["starbox.css"],
  templateUrl: "starbox.html",
})
export class StarboxComponent {
  @Input() average: number = 0;

  constructor() {}
}
