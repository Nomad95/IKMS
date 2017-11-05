import {Component, OnDestroy, OnInit} from "@angular/core";
import {NotificationService} from "../../services/notification.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Notification} from "../model/notification";
import {ConfirmationService} from "primeng/primeng";
import {GenericPage} from "../model/genericPage";

@Component({
  selector: 'notification',
  templateUrl: './notification-list.component.html',
  providers: [NotificationService, ConfirmationService]
})
export class NotificationComponent implements OnInit, OnDestroy {


  private isLoading: boolean = true;
  private pageWithNotifications: GenericPage<Notification>;
  private notifications: Array<Notification>;
  private indexesOfPage;
  private notificationsToDelete;
  private currentPage: number;
  private sub:any;
  private countUnreadNotifications = {
    count:''
  };

  constructor(private notificationService: NotificationService,
              private route: ActivatedRoute,
              private router: Router) {
    this.currentPage = 0;
  }

  ngOnInit() {
    this.getIndexOfPageAndGetMyNotifications();
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  getMyNotificationsByPage(numberOfPage: number): void{
    console.log(this.notifications);
    this.notifications = [];
    this.indexesOfPage = [];
    this.isLoading = true;
    console.log(this.notifications);
    this.notificationService
      .getMyNotifications(numberOfPage)
      .subscribe( result => {
        this.pageWithNotifications = result;
        console.log(result);
        for(let note of this.pageWithNotifications.content){
          note.checked = false;
          this.notifications.push(note);
          console.log("Check: "+note.checked);
        }
        this.generateTabForPagination(this.pageWithNotifications.totalPages);
        this.countMyUnreadNotifications();
        console.log(this.notifications);
        this.isLoading = false;
      }, err => {
        this.isLoading = false;
      })
  }

  // generate list with number of pages
  generateTabForPagination(numberOfPage) {
    for (var i = 0; i < numberOfPage; i++)
      this.indexesOfPage.push(i+1);
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
    var currentUrl = this.router.url;
    var refreshUrl = currentUrl.indexOf('messagebox/sent') > -1 ? '/messagebox' : 'messagebox/sent';
    this.router.navigateByUrl(refreshUrl).then(() => this.router.navigateByUrl(currentUrl));
  }
}
