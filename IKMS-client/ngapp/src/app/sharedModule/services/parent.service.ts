import {Injectable} from "@angular/core";
import {Headers, Http} from "@angular/http";
import {TokenUtils} from "../../commons/util/token-utils";
import {Observable} from "rxjs/Observable";
import {Page} from "../../commons/model/page";
import {Parent} from "../../adminSiteModule/menu/model/parent/parent";

@Injectable()
export class ParentService {
  public token: string;
  public username: string;
  private headers: Headers;

  constructor(private http: Http) {
    let currentUser = JSON.parse(TokenUtils.getToken());
    this.token = currentUser && currentUser.token;
    this.headers= TokenUtils.createHeaderWithToken();
  }

  getParentGeneralDetails(size, page): Observable<Page>{
    return this.http.get('api/parent/general?page=' + page + '&size=' + size, {headers: this.headers})
      .map( res => res.json());
  }

  getParent(parentId): Observable<Parent>{
    return this.http.get('api/parent/' + parentId, {headers: this.headers})
      .map( res => res.json());
  }
  updateParent(parent): Observable<Parent>{
    return this.http.put('api/parent', JSON.stringify(parent), {headers: this.headers})
      .map( res => res.json());
  }

  deleteParent(parentId): Observable<any>{
    return this.http.delete('api/parent/'+parentId, {headers: this.headers});
  }
  
  getAllMinimal(): Observable<any>{
      return this.http.get('api/parent/minimal/all', {headers: this.headers})
        .map(res => res.json());
  }

}
