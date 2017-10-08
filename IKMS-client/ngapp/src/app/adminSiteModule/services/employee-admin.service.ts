import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
import { TokenUtils } from "../../commons/util/token-utils";
import {Page} from "../../commons/model/page";
import {Employee} from "../menu/model/employee/employee";

@Injectable()
export class EmployeeAdminService{
    public token: string;
    public username: string;
    private headers: Headers;
    
    constructor(private http: Http) {
        let currentUser = JSON.parse(TokenUtils.getToken());
        this.token = currentUser && currentUser.token;
        this.headers= TokenUtils.createHeaderWithToken();
    }
    
    getEmployeeGeneralDetails(size, page): Observable<Page>{
        return this.http.get('api/employee/general?page=' + page + '&size=' + size, {headers: this.headers})
            .map( res => res.json());
    }
    
    getEmployee(employeeId): Observable<Employee>{
        return this.http.get('api/employee/' + employeeId, {headers: this.headers})
            .map( res => res.json());
    }
    
    updateEmployee(employee): Observable<Employee>{
        return this.http.put('api/employee', JSON.stringify(employee), {headers: this.headers})
            .map( res => res.json());
    }
  
}
