import { Component, Input } from "@angular/core";

@Component({
  selector: "starbox",
  styleUrls: ["starbox.css"],
  templateUrl: "starbox.html",
})
export class StarboxComponent {
  @Input() average: number = 0;

  constructor() {}
}
