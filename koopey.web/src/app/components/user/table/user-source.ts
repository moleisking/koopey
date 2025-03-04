import { MatTableDataSource } from "@angular/material/table";
import { Subscription } from "rxjs/internal/Subscription";
import { User } from "../../../models/user";
import { AlertService } from "../../../services/alert.service";
import { UserService } from "../../../services/user.service";

export class UserDataSource extends MatTableDataSource<User> {

    public users: Array<User> = new Array<User>();
    private userSubscription: Subscription = new Subscription();

    constructor(private alertService: AlertService, private userService: UserService,) {
        super();
        this.getUsers();
    }

    public getUsers() {
        this.userSubscription = this.userService.getUsers().subscribe(
            (users: Array<User>) => { this.users = users; },
            (error: Error) => { this.alertService.error(error.message); },
            () => { /*this.refreshDataSource();*/ }
        );
    }

    disconnect() {
        this.userSubscription.unsubscribe();
        super.disconnect();
    }
}