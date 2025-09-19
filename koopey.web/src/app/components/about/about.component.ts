import { Component, OnInit } from "@angular/core";
import { HomeService } from "../../services/home.service";

@Component({
  selector: "about-component",
  standalone: false,
  templateUrl: "about.html",
  styleUrls: ["about.css"],
})
export class AboutComponent implements OnInit {
  private message: string;

  constructor(private web: HomeService) {
    this.message = "Here will come About Text from backend.";
  }

  ngOnInit() {
    this.getAboutText();
  }

  getAboutText() {
    /*this.web.about().subscribe(
            (text : any) => this.message = text,
            (error: any )=> this.message = <any>error
        );*/
  }
}
