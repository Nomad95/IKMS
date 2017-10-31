import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
import { TokenUtils } from "../../commons/util/token-utils";
import { User} from '../../adminSiteModule/model/user/user';
import { PersonalData } from '../../adminSiteModule/model/personalData/personal-data';
import { Employee } from '../../adminSiteModule/model/employee/employee';
import { Parent } from '../../adminSiteModule/menu/model/parent/parent';
import { Address } from '../../adminSiteModule/model/address/address';

@Injectable()
export class RegistrationService {
  public token: string;
  public username: string;
  private headers: Headers;

  constructor(private http: Http) {
    let currentUser = JSON.parse(TokenUtils.getToken());
    this.token = currentUser && currentUser.token;
    this.headers = TokenUtils.createHeaderWithToken();
  }

  createUser(user): Observable<User> {
    return this.http.post('api/user', JSON.stringify(user), { headers: this.headers })
      .map(res => res.json());
  }

  createPersonalData(personalData): Observable<PersonalData>{
    return this.http.post('api/personalData', JSON.stringify(personalData), {headers: this.headers})
    .map( res => res.json());
  }

  createAddress(address): Observable<Address>{
    return this.http.post('api/address', JSON.stringify(address), {headers: this.headers})
    .map( res => res.json());
  }

  createAdmin(admin): Observable<Employee> {
    return this.http.post('api/employee', JSON.stringify(admin), { headers: this.headers })
      .map(res => res.json());
  }

  createEmployee(employee): Observable<Employee> {
    return this.http.post('api/employee', JSON.stringify(employee), { headers: this.headers })
      .map(res => res.json());
  }

  createParent(parent): Observable<Parent> {
    return this.http.post('api/parent', JSON.stringify(parent), { headers: this.headers })
      .map(res => res.json());
  }
}
