import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
import { TokenUtils } from "../../commons/util/token-utils";
import {Group} from "../../adminSiteModule/model/group/group";
import {Page} from "../../commons/model/page";
import {Classroom} from "../../adminSiteModule/model/classroom/classroom";

@Injectable()
export class ClassroomService{
    public token: string;
    public username: string;
    private headers: Headers;
    
    constructor(private http: Http) {
        let currentUser = JSON.parse(TokenUtils.getToken());
        this.token = currentUser && currentUser.token;
        this.headers = TokenUtils.createHeaderWithToken();
    
    }
    
    getClassroomList(size, page): Observable<Page>{
        return this.http.get('api/classroom?page=' + page + '&size=' + size, {headers: this.headers})
        .map( res => res.json());
    }
    
    getClassroom(classroomId): Observable<Classroom>{
        return this.http.get('api/classroom/' + classroomId, {headers: this.headers})
            .map( res => res.json());
    }
    
    updateClassroom(classroom): Observable<Classroom>{
        return this.http.put('api/classroom', JSON.stringify(classroom), {headers: this.headers})
            .map( res => res.json());
    }
    
    createClassroom(classroom): Observable<Classroom>{
        return this.http.post('api/classroom', JSON.stringify(classroom), {headers: this.headers})
        .map( res => res.json());
    }
    
    deleteClassroom(classroomId): Observable<any>{
        return this.http.delete('api/classroom/' + classroomId, {headers: this.headers})
        .map( res => res.json());
    }
    
    getClassroomMinimal(): Observable<Classroom>{
        return this.http.get('api/classroom/minimal/all', {headers: this.headers})
        .map( res => res.json());
    }
}
