import { Injectable } from "@angular/core";
import { DomSanitizer, SafeUrl } from "@angular/platform-browser";
import { Environment } from "src/environments/environment";
import { Location, LocationType } from "../../models/location";

@Injectable()
export abstract class BaseComponent {
  constructor(public sanitizer: DomSanitizer) {}

  public getAlias(): string {
    return localStorage.getItem("alias")!;
  }

  public getAvatar(): SafeUrl {
    if (localStorage.getItem("avatar")) {
      return this.sanitizer.bypassSecurityTrustUrl(
        localStorage.getItem("avatar")!
      );
    } else {
      return "/images/default-user.svg";
    }
  }

  public getLanguage() {
    return localStorage.getItem("language")!;
  }

  public getPosition(): Location {
    let location: Location = new Location();
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        location.latitude = position.coords.latitude;
        location.longitude = position.coords.longitude;
        location.position = Location.convertToPosition(
          location.longitude,
          location.latitude
        );
        location.type = LocationType.PresentPosition;
      });
    } else {
      location.latitude = Environment.Default.Latitude;
      location.longitude = Environment.Default.Longitude;
    }
    return location;
  }

  public isAuthenticated() {
    if (localStorage.getItem("token") !== null) {
      return true;
    } else {
      return false;
    }
  }
}
