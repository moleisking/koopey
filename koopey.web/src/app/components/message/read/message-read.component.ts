import { ActivatedRoute } from "@angular/router";
import { AlertService } from "../../../services/alert.service";
import { ChangeDetectionStrategy, Component, OnDestroy, OnInit } from "@angular/core";
import { Subscription } from "rxjs";
import { Message } from "../../../models/message";
import { MessageService } from "../../../services/message.service";
import { User } from "../../../models/user";
import { MatCardModule } from "@angular/material/card";

@Component({
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [MatCardModule],
  selector: "message-read",
  standalone: true,
  styleUrls: ["message-read.css"],
  templateUrl: "message-read.html",
})
export class MessageReadComponent implements OnInit, OnDestroy {
  public message: Message = new Message();
  private messageSubscription: Subscription = new Subscription();
  public user: User = new User();

  constructor(
    private alertService: AlertService,
    private messageService: MessageService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.getMessage();
  }

  ngOnDestroy() {
    if (this.messageSubscription) {
      this.messageSubscription.unsubscribe();
    }
  }

  private getMessage() {
    this.route.params.subscribe((p) => {
      let id = p["id"];
      if (id) {
        this.messageService.read(id).subscribe(
          (message: Message) => {
            this.message = message;
          },
          (error: Error) => {
            this.alertService.error(error.message);
          }
        );
      } else {
        this.messageSubscription = this.messageService.getMessage().subscribe(
          (message: Message) => {
            this.message = message;
          },
          (error: Error) => {
            this.alertService.error(error.message);
          }
        );
      }
    });
  }
}
