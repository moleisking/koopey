import { Injectable } from "@angular/core";
import {
  CanActivate,
  Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
} from "@angular/router";
import { UserService } from "../services/user.service";
import { AlertService } from "../services/alert.service";

@Injectable()
export class RoutesManager implements CanActivate {
  private LOG_HEADER: string = "RouteManager:";

  constructor(private userService: UserService, private router: Router) {}

  public canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ) {
    if (
      localStorage.getItem("id") !== null &&
      localStorage.getItem("name") !== null &&
      localStorage.getItem("token") !== null
    ) {
      // user already logged in
      if (route.url[0].path === "register") {
        console.log(this.LOG_HEADER + "User Found");
        //user can't be added again
        this.router.navigate(["/dashboard"]);
        return true;
      } else {
        //resume to authenticated link
        return true;
      }
    } else {
      // not logged in so redirect to login page with the return url
      console.log(this.LOG_HEADER + "No User Found");
      this.router.navigate(["/login"]);
      return false;
    }
  }
}
