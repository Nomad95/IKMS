import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
import { TokenUtils } from "../../commons/util/token-utils";

@Injectable()
export class FileUploadService {
    public token: string;
    public username: string;
    private headers: Headers;
    
    constructor(private http: Http) {
        let currentUser = JSON.parse(TokenUtils.getToken());
        this.token = currentUser && currentUser.token;
        this.headers = TokenUtils.createHeaderWithToken();
    }
    
    uploadDidacticMaterial(file){
        return Observable.fromPromise(new Promise((resolve, reject) => {
            let headers = new Headers();
            let xhr = new XMLHttpRequest();
    
            xhr.open('POST', '/api/upload', true);
            xhr.setRequestHeader('Auth-token', this.token);
            xhr.setRequestHeader('role', 'ADMIN');
            xhr.setRequestHeader('enctype', 'multipart/form-data');
            xhr.send(file);
        }));
    }
    
    uploadDidacticMaterialByEmployee(file){
        return Observable.fromPromise(new Promise((resolve, reject) => {
            let headers = new Headers();
            let xhr = new XMLHttpRequest();
            xhr.open('POST', '/api/upload', true);
            xhr.setRequestHeader('Auth-token', this.token);
            xhr.setRequestHeader('role', 'EMPLOYEE');
            xhr.setRequestHeader('enctype', 'multipart/form-data');
            xhr.send(file);
        }));
    }
    
    getDidacticMaterial(materialId): Observable<any>{
        return this.http.get('/api/upload/' + materialId, {headers: this.headers})
            .map( res => res.json());
    }
    
    getFileTree(): Observable<any>{
        return this.http.get('/api/upload/tree', {headers: this.headers})
        .map( res => res.json());
    }
}