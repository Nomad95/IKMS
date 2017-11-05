

import {Injectable} from "@angular/core";
import {Headers, Http} from "@angular/http";
import {TokenUtils} from "../../commons/util/token-utils";
import {Observable} from "rxjs/Observable";
import {Notification} from "../notification/model/notification";

@Injectable()
export class NotificationService {
  public token: string;
  public username: string;
  private headers: Headers;
  private url: string;

  constructor(private http: Http) {
    let currentUser = JSON.parse(TokenUtils.getToken());
    this.token = currentUser && currentUser.token;
    this.headers = TokenUtils.createHeaderWithToken();

  }

  sendNotification(notifcation: Notification, recipient_username):Observable<Notification>{
    this.url = 'api/notification/user/'+ recipient_username;
    return this.http.post(this.url, JSON.stringify(notifcation), {headers: this.headers})
      .map(res => res.json());
  }

  getMyNotifications(numberOfPage:number): Observable<any> {
    this.url = '/api/notification/myNotifications?page='+numberOfPage+'&size=10';
    return this.http.get(this.url, {headers: this.headers})
      .map(res => res.json());
  }

  deleteNotification(id): Observable<void> {
    this.url = '/api/notification/myNotifications/' + id;
    return this.http.delete(this.url, {headers: this.headers})
      .map(() => null);
  }

  countMyUnreadNotifications(){
    this.url = "/api/notification/myNotifications/quantity/unread";
    return this.http.get(this.url,{headers :this.headers})
      .map(res => res.json());
  }

}
