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
        let headers = new Headers();
        let xhr = new XMLHttpRequest();
        
        xhr.open('POST', '/api/upload', true);
        xhr.setRequestHeader('Auth-token', this.token);
        xhr.setRequestHeader('enctype', 'multipart/form-data');
        xhr.send(file);
        
       /* headers.append('Content-Type', 'multipart/form-data');
        headers.append('Accept', 'application/json');
        headers.append('Auth-token', this.token);*/
        
        /*return this.http.post('api/upload', file, {headers: headers})
        .map( res => res.json());*/
    }
    
    getDidacticMaterial(): Observable<any>{
        return this.http.get('/api/upload', {headers: this.headers})
            .map( res => res.json());
    }
}