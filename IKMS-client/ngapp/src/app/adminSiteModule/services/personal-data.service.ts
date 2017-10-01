import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
import { TokenUtils } from "../../commons/util/token-utils";
import {PersonalData} from "../menu/model/personalData/personal-data";

@Injectable()
export class PersonalDataAdminService{
    public token: string;
    public username: string;
    private headers: Headers;
    
    constructor(private http: Http) {
        let currentUser = JSON.parse(TokenUtils.getToken());
        this.token = currentUser && currentUser.token;
    }
    
    getPersonalData(personalDataId): Observable<PersonalData>{
        let headers: Headers = TokenUtils.createHeaderWithToken();
        return this.http.get('api/personalData/' + personalDataId, {headers: headers})
        .map( res => res.json());
    }
  
}
