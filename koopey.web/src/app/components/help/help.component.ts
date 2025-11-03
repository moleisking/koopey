import { ChangeDetectionStrategy, Component } from "@angular/core";

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  selector: "help-component",
  standalone: true,
  templateUrl: "help.html",
})
export class HelpComponent { }
