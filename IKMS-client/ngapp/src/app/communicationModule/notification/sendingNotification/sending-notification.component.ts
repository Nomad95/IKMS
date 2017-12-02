import {NotificationService} from "../../../sharedModule/services/notification.service";
import {Component, Input, OnInit, ViewChild} from "@angular/core";
import {EnumProvider} from "../../../commons/util/enum-provider";
import {NewNotification} from "../model/new-notification";
import {Message} from "primeng/primeng";
import {ErrorHandler} from "../../../commons/util/error-handler";

@Component({
  selector: 'sending-notification',
  templateUrl: './sending-notification.component.html',
  providers: [NotificationService,EnumProvider]
})
export class SendingNotificationComponent implements OnInit{

  private notification: NewNotification;
  private priorityOptions = EnumProvider.PRIORITY;
  private msgs: Message[] = [];

  @Input() recipientUsername;
  @ViewChild('notificationForm') form;

  constructor(private notificationService: NotificationService,
              private enumProvider: EnumProvider) {
  }

  ngOnInit(): void {
    this.priorityOptions = this.enumProvider.translateToDropdown(this.priorityOptions);
    this.clearForm();
  }

  sendNotification(notification: NewNotification):void{
    this.notificationService
      .sendNotification(notification,this.recipientUsername )
      .subscribe( newNothification => {
        this.notification;
      }, error2 => {
        this.msgs = ErrorHandler.handleGenericServerError(error2);
      })
  }

  clearForm(){
    this.notification = new NewNotification();
    this.form.reset();
    this.notification.priority = (<any>this.priorityOptions[0]).value;
  }

}
