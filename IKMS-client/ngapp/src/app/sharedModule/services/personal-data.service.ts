import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
import { TokenUtils } from "../../commons/util/token-utils";
import {PersonalData} from "../../adminSiteModule/model/personalData/personal-data";
import {MinimalDto} from "../../adminSiteModule/model/minimal-dto";

@Injectable()
export class PersonalDataService{
    public token: string;
    public username: string;
    private headers: Headers;

    constructor(private http: Http) {
        let currentUser = JSON.parse(TokenUtils.getToken());
        this.token = currentUser && currentUser.token;
        this.headers = TokenUtils.createHeaderWithToken();
    }

    getPersonalData(personalDataId): Observable<PersonalData>{
        return this.http.get('api/personalData/' + personalDataId, {headers: this.headers})
            .map( res => res.json());
    }
    
    getCurrentUserName(): Observable<MinimalDto>{
        return this.http.get('api/personalData/myName' , {headers: this.headers})
        .map( res => res.json());
    }

    updatePersonalData(personalData): Observable<PersonalData>{
        return this.http.put('api/personalData', JSON.stringify(personalData), {headers: this.headers})
            .map( res => res.json());
    }

    createPersonalData(personalData): Observable<PersonalData>{
        return this.http.post('api/personalData', JSON.stringify(personalData), {headers: this.headers})
        .map( res => res.json());
    }

}
