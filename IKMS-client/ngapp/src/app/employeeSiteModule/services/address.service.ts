import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
import { TokenUtils } from "../../commons/util/token-utils";
import { Address } from "../model/address/address";

@Injectable()
export class AddressEmployeeService{
    public token: string;
    public username: string;
    private headers: Headers;
    
    constructor(private http: Http) {
        let currentUser = JSON.parse(TokenUtils.getToken());
        this.token = currentUser && currentUser.token;
        this.headers = TokenUtils.createHeaderWithToken();
    }
    
    getAddress(addressId): Observable<Address>{
        return this.http.get('api/address/' + addressId, {headers: this.headers})
            .map( res => res.json());
    }
  
    getAddressesByPersonalDataId(personalDataId): Observable<Address[]>{
        return this.http.get('api/address/personalData/' + personalDataId, {headers: this.headers})
            .map( res => res.json());
    }
    
    updateAddress(address): Observable<Address>{
        return this.http.put('api/address', JSON.stringify(address) ,{headers: this.headers})
            .map( res => res.json());
    }
    
    createAddress(address): Observable<Address>{
        return this.http.post('api/address', JSON.stringify(address) ,{headers: this.headers})
            .map( res => res.json());
    }
    
    getAddressesByParentId(parentId): Observable<Address[]>{
        return this.http.get('api/address/parent/' + parentId, {headers: this.headers})
        .map( res => res.json());
    }
}
