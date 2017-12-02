import {Injectable} from "@angular/core";
import {Headers, Http} from "@angular/http";
import {TokenUtils} from "../../commons/util/token-utils";
import {Observable} from "rxjs/Observable";
import {Count} from "../../communicationModule/messagebox/model/count";

@Injectable()
export class MessageService {

  public token: string;
  public username: string;
  private headers: Headers;
  private url: string;
  private unreadMessageAmount: number;

  constructor(private http: Http) {
    let currentUser = JSON.parse(TokenUtils.getToken());
    this.token = currentUser && currentUser.token;
    this.headers = TokenUtils.createHeaderWithToken();
  }

  sendMessage(message,recipientUsername ): Observable<any>{
    this.url = '/api/message/user/'+recipientUsername;
    return this.http.post(this.url,JSON.stringify(message),{headers :this.headers})
      .map(res => res.json());
  }

  getMyReceivedMessages(numberOfPage: number): Observable<any> {
    this.url = 'api/message/myMessages/received?page='+numberOfPage+'&size=10';
    return this.http.get(this.url, {headers: this.headers})
      .map(res => res.json());
  }

  getMySentMessages(numberOfPage: number): Observable<any> {
    this.url = '/api/message/myMessages/sent?page='+numberOfPage+'&size=10';
    return this.http.get(this.url, {headers: this.headers})
      .map(res => res.json());

  }
  getReceivedMessageDetails(id): Observable<any> {
    this.url = '/api/message/myMessages/received/'+id;
    return this.http.get(this.url, {headers: this.headers})
      .map(res => res.json());
  }

  getSentMessageDetails(id): Observable<any> {
    this.url = '/api/message/myMessages/sent/'+id;
    return this.http.get(this.url, {headers: this.headers})
      .map(res => res.json());
  }

  deleteMyReceivedMessage(id): Observable<void> {
    this.url = '/api/message/myMessages/received/' + id;
    return this.http.delete(this.url, {headers: this.headers})
      .map(() => null);
  }

  deleteMySentMessage(id): Observable<void> {
    this.url = '/api/message/myMessages/sent/' + id;
    return this.http.delete(this.url, {headers: this.headers})
      .map(() => null);
  }

  countMyUnreadMessages(): Observable<Count> {
    this.url = "/api/message/myMessages/received/quantity/unread";

    return this.http.get(this.url,{headers :this.headers})
      .map(res => res.json());
  }

  setAmountOfUnreadMessage(amount):void{
    this.unreadMessageAmount = +amount;
  }

  getAmountOfUnreadMessage(): number{

    return this.unreadMessageAmount;
  }

}
