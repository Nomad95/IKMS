import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
import { TokenUtils } from "../../commons/util/token-utils";
import {ScheduleActivity} from "../../adminSiteModule/model/schedule/schedule-activity";
import {ScheduleActivityDiaryDetail} from "../../adminSiteModule/model/schedule/schedule-activity-diary-detail";

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
        return this.http.get('api/schedule/all?for=&id=', {headers: this.headers})
            .map( res => res.json());
    }
    
    getActivitiesFor(forWho, id): Observable<ScheduleActivity[]>{
        return this.http.get('api/schedule/all?for=' + forWho + '&id=' + id, {headers: this.headers})
        .map( res => res.json());
    }
    
    getActivitiesForLoggedEmployee(): Observable<ScheduleActivity[]>{
        return this.http.get('api/schedule/all/employee', {headers: this.headers})
        .map( res => res.json());
    }
    
    getActivity(activityId): Observable<ScheduleActivity>{
        return this.http.get('api/schedule/' + activityId, {headers: this.headers})
        .map( res => res.json());
    }
    
    getActivityDiaryDetails(activityId): Observable<ScheduleActivityDiaryDetail>{
        return this.http.get('api/schedule/diary/' + activityId, {headers: this.headers})
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
    
    validateActivity(activity): Observable<string[]>{
        return this.http.post('api/schedule/validate/single', activity, {headers: this.headers})
        .map( res => res.json());
    }
    
    validateActivities(activities): Observable<ScheduleActivity[]>{
        return this.http.post('api/schedule/validate/many', activities, {headers: this.headers})
        .map( res => res.json());
    }
    
}
