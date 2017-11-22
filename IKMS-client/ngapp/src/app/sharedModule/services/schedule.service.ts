import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
import { TokenUtils } from "../../commons/util/token-utils";
import {ScheduleActivity} from "../../adminSiteModule/model/schedule/schedule-activity";

@Injectable()
export class ScheduleService{
    public token: string;
    public username: string;
    private headers: Headers;

    constructor(private http: Http) {
        let currentUser = JSON.parse(TokenUtils.getToken());
        this.token = currentUser && currentUser.token;
        this.headers = TokenUtils.createHeaderWithToken();
    }

    getActivities(): Observable<ScheduleActivity[]>{
        return this.http.get('api/schedule/all', {headers: this.headers})
            .map( res => res.json());
    }
    
    createActivity(activity): Observable<ScheduleActivity>{
        return this.http.post('api/schedule', JSON.stringify(activity), {headers: this.headers})
        .map( res => res.json());
    }
    
    createActivities(activities): Observable<ScheduleActivity[]>{
        return this.http.post('api/schedule/addMany', JSON.stringify(activities), {headers: this.headers})
        .map( res => res.json());
    }
    
    deleteActivity(activityId): Observable<any>{
        return this.http.delete('api/schedule/' + activityId, {headers: this.headers})
        .map( res => res.json());
    }
    
}
