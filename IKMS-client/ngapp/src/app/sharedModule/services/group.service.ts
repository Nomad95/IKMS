import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
import { TokenUtils } from "../../commons/util/token-utils";
import {Group} from "../../adminSiteModule/model/group/group";
import {Page} from "../../commons/model/page";

@Injectable()
export class GroupService{
    public token: string;
    public username: string;
    private headers: Headers;
    
    constructor(private http: Http) {
        let currentUser = JSON.parse(TokenUtils.getToken());
        this.token = currentUser && currentUser.token;
        this.headers = TokenUtils.createHeaderWithToken();
    
    }
    
    getGroupList(size, page): Observable<Page>{
        return this.http.get('api/group?page=' + page + '&size=' + size, {headers: this.headers})
        .map( res => res.json());
    }
    
    getGroup(groupId): Observable<Group>{
        return this.http.get('api/group/' + groupId, {headers: this.headers})
            .map( res => res.json());
    }
    
    updateGroup(group): Observable<Group>{
        return this.http.put('api/group', JSON.stringify(group), {headers: this.headers})
            .map( res => res.json());
    }
    
    createGroup(group): Observable<Group>{
        return this.http.post('api/group', JSON.stringify(group), {headers: this.headers})
        .map( res => res.json());
    }
    
    deleteGroup(groupId): Observable<any>{
        return this.http.delete('api/group/' + groupId, {headers: this.headers})
        .map( res => res.json());
    }
}
