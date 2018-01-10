import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
import { TokenUtils } from "../../commons/util/token-utils";
import {ScheduleActivity} from "../../adminSiteModule/model/schedule/schedule-activity";
import {ScheduleActivityDiaryDetail} from "../../adminSiteModule/model/schedule/schedule-activity-diary-detail";
import {RegisterDto} from "../../employeeSiteModule/model/diary/registerDto";
import {RegisterEntryDto} from "../../employeeSiteModule/model/diary/registerEntryDto";

@Injectable()
export class RegisterService{
    public token: string;
    public username: string;
    private headers: Headers;

    constructor(private http: Http) {
        let currentUser = JSON.parse(TokenUtils.getToken());
        this.token = currentUser && currentUser.token;
        this.headers = TokenUtils.createHeaderWithToken();
    }
    
    createRegisterWithPresence(registerDto): Observable<RegisterDto>{
        return this.http.post('api/register', registerDto, {headers: this.headers})
        .map( res => res.json());
    }
    
    addEntryToRegister(registerEntryDto): Observable<RegisterEntryDto>{
        return this.http.post('api/register/entry', registerEntryDto, {headers: this.headers})
        .map( res => res.json());
    }
    
    getRegisterForChild(criteria): Observable<RegisterDto>{//todo
        return this.http.get('api/register/all?for=&id=', {headers: this.headers})
            .map( res => res.json());
    }
}
