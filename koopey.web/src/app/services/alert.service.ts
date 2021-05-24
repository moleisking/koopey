import { Injectable } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Observable } from "rxjs";
import { Subject } from "rxjs";
import { TranslateService } from "@ngx-translate/core";

@Injectable()
export class AlertService {
  //private subject = new Subject<any>();
  // private keepAfterNavigationChange = false;

  private TIME_TO_LIVE: number = 1000;

  constructor(
    /*private router: Router,*/ public snackBar: MatSnackBar,
    private translateService: TranslateService
  ) {
    // clear alert message on route change
    /*router.events.subscribe(event => {
            if (event instanceof NavigationStart) {
                if (this.keepAfterNavigationChange) {
                    // only keep for a single location change
                    this.keepAfterNavigationChange = false;
                } else {
                    // clear alert
                    this.subject.next();
                }
            }
        });*/
  }

  public success(message: string) {
    console.log("SUCCESS:" + message);
    //Translate header
    var header = "";
    this.translateService
      .get("SUCCESS")
      .subscribe((translatedPhrase: string) => {
        header = <any>translatedPhrase;
      });
    //Translate message
    var content = "";
    this.translateService.get(message).subscribe((translatedPhrase: string) => {
      content = <any>translatedPhrase;
    });
    //Show snackbar
    this.snackBar.open(content, header, {
      duration: this.TIME_TO_LIVE,
    });
  }

  public info(message: string) {
    console.log("INFO:" + message);
    //Translate header
    var header = "";
    this.translateService.get("INFO").subscribe((translatedPhrase: string) => {
      header = <any>translatedPhrase;
    });
    //Translate message
    var content = "";
    this.translateService.get(message).subscribe((translatedPhrase: string) => {
      content = <any>translatedPhrase;
    });
    //Show snackbar
    this.snackBar.open(content, header, {
      duration: this.TIME_TO_LIVE,
    });
  }

  public warning(message: string) {
    console.log("WARNING:" + message);
    //Translate header
    var header = "";
    this.translateService
      .get("WARNING")
      .subscribe((translatedPhrase: string) => {
        header = <any>translatedPhrase;
      });
    //Translate message
    var content = "";
    this.translateService.get(message).subscribe((translatedPhrase: string) => {
      content = <any>translatedPhrase;
    });
    //Show snackbar
    this.snackBar.open(content, header, {
      duration: this.TIME_TO_LIVE,
    });
  }

  public error(message: string) {
    console.log("ERROR:" + message);
    //Translate header
    var header = "";
    this.translateService.get("ERROR").subscribe((translatedPhrase: string) => {
      header = <any>translatedPhrase;
    });
    //Translate message
    var content = "";
    this.translateService.get(message).subscribe((translatedPhrase: string) => {
      content = <any>translatedPhrase;
    });
    //Show snackbar
    this.snackBar.open(content, header, {
      duration: this.TIME_TO_LIVE,
    });
  }
}
