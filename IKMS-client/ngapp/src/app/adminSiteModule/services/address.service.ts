import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
import { TokenUtils } from "../../commons/util/token-utils";
import { Address } from "../menu/model/address/address";

@Injectable()
export class AddressAdminService{
    public token: string;
    public username: string;
    private headers: Headers;
    
    constructor(private http: Http) {
        let currentUser = JSON.parse(TokenUtils.getToken());
        this.token = currentUser && currentUser.token;
    }
    
    getAddress(addressId): Observable<Address>{
        let headers: Headers = TokenUtils.createHeaderWithToken();
        return this.http.get('api/address/' + addressId, {headers: headers})
        .map( res => res.json());
    }
  
    getAddressesByPersonalDataId(personalDataId): Observable<Address[]>{
        let headers: Headers = TokenUtils.createHeaderWithToken();
        return this.http.get('api/address/personalData/' + personalDataId, {headers: headers})
        .map( res => res.json());
    }
}
