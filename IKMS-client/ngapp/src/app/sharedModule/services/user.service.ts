import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
import { TokenUtils } from "../../commons/util/token-utils";
import {Page} from "../../commons/model/page";
import {MinimalDto} from "../../adminSiteModule/model/minimal-dto";

@Injectable()
export class UserService{
    public token: string;
    public username: string;
    private headers: Headers;

    constructor(private http: Http) {
        let currentUser = JSON.parse(TokenUtils.getToken());
        this.token = currentUser && currentUser.token;
        this.headers = TokenUtils.createHeaderWithToken();
    }
    
    getUsers(size, page): Observable<Page>{
        return this.http.get('api/user?page=' + page + '&size=' + size, {headers: this.headers})
        .map( res => res.json());
    }
    
    getCurrentUser(): Observable<MinimalDto>{
        return this.http.get('auth/whoami', {headers: this.headers})
        .map( res => res.json());
    }
    
    deleteUser(userId): Observable<any>{
        return this.http.delete('api/user/' + userId, {headers: this.headers})
        .map( res => res.json());
    }
    
    updateUser(user){
        return this.http.put('api/user', user,{headers: this.headers})
        .map( res => res.json());
    }
}
