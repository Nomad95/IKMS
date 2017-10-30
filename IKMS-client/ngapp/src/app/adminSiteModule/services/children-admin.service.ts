import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
import { TokenUtils } from "../../commons/util/token-utils";
import {Page} from "../../commons/model/page";
import {Child} from "../menu/model/children/child";
import {ChildGeneral} from "../menu/model/children/child-general";

@Injectable()
export class ChildrenAdminService{
    public token: string;
    public username: string;
    private headers: Headers;
    
    constructor(private http: Http) {
        let currentUser = JSON.parse(TokenUtils.getToken());
        this.token = currentUser && currentUser.token;
        this.headers= TokenUtils.createHeaderWithToken();
    }
    
    getChildrenGeneralDetails(size, page): Observable<Page>{
        return this.http.get('api/child/general?page=' + page + '&size=' + size, {headers: this.headers})
            .map( res => res.json());
    }
    
    getChildrenGeneralDetailsByIds(ids): Observable<ChildGeneral[]>{
        return this.http.post('api/child/general', JSON.stringify(ids),{headers: this.headers})
        .map( res => res.json());
    }
    
    getChildrenGrouplessMinimal(): Observable<any[]>{
        return this.http.get('api/child/minimal/groupless',{headers: this.headers})
        .map( res => res.json());
    }
    
    getChild(childId): Observable<Child>{
        return this.http.get('api/child/' + childId, {headers: this.headers})
            .map( res => res.json());
    }
    
    updateChild(child): Observable<Child>{
        return this.http.put('api/child', JSON.stringify(child), {headers: this.headers})
            .map( res => res.json());
    }
    
    deleteChild(childId): Observable<any>{
        return this.http.delete('api/child/'+childId, {headers: this.headers});
    }
    
    createChild(child): Observable<Child>{
        return this.http.post('api/child', JSON.stringify(child), {headers: this.headers})
        .map( res => res.json());
    }
}
