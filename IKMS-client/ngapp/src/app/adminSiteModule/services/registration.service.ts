import {Injectable} from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
import {TokenUtils} from "../../commons/util/token-utils";

@Injectable()
export class RegistrationService{
  public token: string;
  public username: string;
  private headers: Headers;

  constructor(private http: Http) {
    let currentUser = JSON.parse(TokenUtils.getToken());
    this.token = currentUser && currentUser.token;
    this.headers = TokenUtils.createHeaderWithToken();
  }


}
