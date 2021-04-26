import { Component, OnInit, OnDestroy } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Subscription } from "rxjs";
import { AlertService } from "../../../services/alert.service";
import { AuthenticationService } from "../../../services/authentication.service";
import {
  ClickService,
  CurrentComponent,
  ActionIcon,
} from "../../../services/click.service";
import { TransactionService } from "../../../services/transaction.service";
import { TranslateService } from "ng2-translate";
import { UserService } from "../../../services/user.service";
import { Config } from "../../../config/settings";
import { Location } from "../../../models/location";
import { Transaction } from "../../../models/transaction";
import { User } from "../../../models/user";
declare var google: any;

@Component({
  selector: "appointment-map-component",
  templateUrl: "appointment-map.html",
  styleUrls: ["appointment-map.css"],
})
export class AppointmentMapComponent implements OnInit, OnDestroy {
  private clickSubscription: Subscription = new Subscription();
  private transactionSubscription: Subscription = new Subscription();
  private transactions: Array<Transaction> = new Array<Transaction>();
  private map: any;

  constructor(
    private alertService: AlertService,
    private clickService: ClickService,
    private route: ActivatedRoute,
    private router: Router,
    private transactionService: TransactionService,
    private translateService: TranslateService
  ) {}

  ngOnInit() {
    this.transactionSubscription = this.transactionService
      .getTransactions()
      .subscribe(
        (transactions) => {
          this.transactions = transactions;
        },
        (error) => {
          this.alertService.error(error);
        },
        () => {}
      );
  }

  ngAfterContentInit() {
    this.clickService.createInstance(
      ActionIcon.LIST,
      CurrentComponent.TransactionMapComponent
    );
    this.clickSubscription = this.clickService
      .getTransactionMapClick()
      .subscribe(() => {
        this.gotoTransactionList();
      });
  }

  ngAfterViewInit() {
    //Create map object
    var mapProp = {
      center: new google.maps.LatLng(
        Config.default_latitude,
        Config.default_longitude
      ),
      zoom: 5,
      mapTypeId: google.maps.MapTypeId.ROADMAP,
    };
    this.map = new google.maps.Map(
      document.getElementById("googleMap"),
      mapProp
    );
    //Full map object
    if (this.transactions && this.transactions.length > 0) {
      for (var i = 0; i <= this.transactions.length; i++) {
        var transaction: Transaction = this.transactions[i];
        for (var j = 0; j <= transaction.users.length; j++) {
          var user = transaction.users[j];
          this.addMarker(
            transaction.location.latitude,
            transaction.location.longitude,
            user.alias,
            user.id
          );
        }
      }
    }
  }

  ngOnDestroy() {
    if (this.clickSubscription) {
      this.clickService.destroyInstance();
      this.clickSubscription.unsubscribe();
    }
    if (this.transactionSubscription) {
      this.transactionSubscription.unsubscribe();
    }
  }

  private addMarker(
    latitude: number,
    longitude: number,
    title: string,
    userId: string
  ) {
    let myLatlng = new google.maps.LatLng(latitude, longitude); //new google.maps.LatLng(23.5454, 90.8785);
    let marker = new google.maps.Marker({
      draggable: true,
      animation: google.maps.Animation.DROP,
      position: myLatlng,
      map: this.map, //set map created here
      title: title,
    });
    marker.addListener("click", () => {
      console.log("marker.addListener click");
      this.router.navigate(["transaction/read/transaction", userId]);
    });
  }

  private gotoTransactionList() {
    this.router.navigate(["/transaction/read/list"]);
  }
}
