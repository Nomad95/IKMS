import {Component, OnInit} from '@angular/core';
import {LoginService} from "../loginModule/service/login.service";
import {MessageService} from "../sharedModule/services/message.service";
import {NotificationService} from "../sharedModule/services/notification.service";

@Component({
  selector: 'admin-site',
  templateUrl: './adminsite.component.html',
    providers: [LoginService]
})
export class AdminSiteComponent implements OnInit{
    private isLoading: boolean = true;

    constructor(private loginService: LoginService,
                private messageService: MessageService,
                private notificationService: NotificationService){}


  ngOnInit(): void {
     this.countUnreadMessageAndSetOnMessageService();
     this.countUnreaNotificationAndSetOnNotificationService()
  }

  logout(){
        this.loginService.logout();
    }

    countUnreadMessageAndSetOnMessageService(){
      this.messageService.countMyUnreadMessages()
        .subscribe(result => {
          this.messageService.setAmountOfUnreadMessage(result.count);
          this.isLoading = false;
        }, error2 => {
          this.isLoading = false;
        });
    }

  countUnreaNotificationAndSetOnNotificationService(){
    this.notificationService.countMyUnreadNotifications()
      .subscribe(result => {
        this.notificationService.setAmountOfUnreadNotification(result.count);
        this.isLoading = false;
      }, error2 => {
        this.isLoading = false;
      });
  }
}
