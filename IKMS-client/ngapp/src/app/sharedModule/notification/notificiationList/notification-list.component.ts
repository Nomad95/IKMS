import {Component, OnDestroy, OnInit} from "@angular/core";
import {NotificationService} from "../../services/notification.service";
import {ActivatedRoute, Data, Router} from "@angular/router";
import {Notification} from "../model/notification";
import {ConfirmationService, MenuItem, Message} from "primeng/primeng";
import {GenericPage} from "../model/genericPage";
import {BreadMaker} from "../../../commons/util/bread-maker";
import {ErrorHandler} from "../../../commons/util/error-handler";

@Component({
  selector: 'notification',
  templateUrl: './notification-list.component.html',
  providers: [NotificationService, ConfirmationService]
})
export class NotificationComponent implements OnInit, OnDestroy {

  private isLoading: boolean = true;
  private pageWithNotifications: GenericPage<Notification>;
  private notifications: Array<Notification>;
  private notificationsToDelete;
  private currentPage: number;
  private sub:any;
  private countUnreadNotifications = {
    count:''
  };
  private msgs: Message[] = [];
  private items: MenuItem[];
  private first;

  constructor(private notificationService: NotificationService,
              private route: ActivatedRoute,
              private router: Router) {
    this.currentPage = 0;
  }

  ngOnInit() {
    this.items = BreadMaker.makeBreadcrumbs("Powiadomienia", "Moje powiadomienia");
    this.getIndexOfPageAndGetMyNotifications();
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  getMyNotificationsByPage(numberOfPage: number): void{
    this.notifications = [];
    this.isLoading = true;
    this.notificationService
      .getMyNotifications(numberOfPage)
      .subscribe( result => {
        this.pageWithNotifications = result;
        for(let note of this.pageWithNotifications.content){
          note.checked = false;
          let date = new Date(note.dateOfSend);
          if(date.toDateString() === new Date(Date.now()).toDateString()){
            note.dateOfSend = new Date(note.dateOfSend).toLocaleTimeString();
          } else {
            note.dateOfSend = new Date(note.dateOfSend).toUTCString();
          }
          this.notifications.push(note);
        }
        this.countMyUnreadNotifications();
        this.first = (this.pageWithNotifications.number)*10;
        this.isLoading = false;
      }, err => {
        this.isLoading = false;
        this.msgs = ErrorHandler.handleGenericServerError(err);
      })
  }

  getIndexOfPageAndGetMyNotifications():void{
    this.sub = this.route.params.subscribe(params => {
      this.currentPage = +params['page'];
      this.getMyNotificationsByPage(this.currentPage);
    });
  }

  countMyUnreadNotifications():void{
    this.notificationService.countMyUnreadNotifications().subscribe(
      result => {
        this.countUnreadNotifications = result;
      },
      err =>{}
    );
  }

  getSelectedOptions() {
    this.notificationsToDelete = [];
    this.notificationsToDelete= this.notifications
      .filter(opt => opt.checked)
      .map(opt => opt.id);
  }

  deleteSelectedNotificatins():void{
    this.getSelectedOptions();

    var countNotifications =  this.notificationsToDelete.length;
    for(let notification of this.notificationsToDelete){
      if(countNotifications == 1)
        this.notificationService
          .deleteNotification(notification)
          .subscribe(()=> {
            this.countMyUnreadNotifications();
          });
      else
        this.notificationService
          .deleteNotification(notification)
          .subscribe();
      countNotifications--;
    }
    this.reloadPage();
  }

  reloadPage():void{
    window.location.reload();
  }

  paginate(event) {
    this.getMyNotificationsByPage(event.page);
  }


}
