import { Injectable } from "@angular/core";
import { DomSanitizer, SafeUrl } from "@angular/platform-browser";
import { LocationHelper } from "src/app/helpers/LocationHelper";
import { LocationType } from "src/app/models/type/LocationType";
import { User } from "src/app/models/user";
import { Environment } from "src/environments/environment";
import { Location } from "../../models/location";

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

  public getUserIdOnly(): User {
    let user: User = new User();
    user.id = localStorage.getItem("id")!;
    return user;
  }

  public getPosition(): Location {
    let location: Location = new Location();
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        location.latitude = position.coords.latitude;
        location.longitude = position.coords.longitude;
        location.position = LocationHelper.convertToPosition(
          location.longitude,
          location.latitude
        );
        location.type = LocationType.Present;
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
