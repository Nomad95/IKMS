import {Injectable} from '@angular/core';
import {Http, Headers, Response} from '@angular/http';
import {Observable} from 'rxjs';
import 'rxjs/add/operator/map'
import {TokenUtils} from "../../commons/util/token-utils";
import {Credentials} from "../model/credentials-model";
import {Router} from "@angular/router";

@Injectable()
export class LoginService {
    public token: string;
    public username: string;
    public redirectUrl: string;
    private headers: Headers;
    private storedRole: string = null;
    
    constructor(private http: Http,private router: Router) {
        // set token if saved in local storage
        let currentUser = JSON.parse(TokenUtils.getToken());
        this.token = currentUser && currentUser.token;
    }
    
    login(credentials: Credentials): Observable<boolean> {
        this.headers = TokenUtils.createSimpleHeader();
        return this.http.post('auth/login', JSON.stringify({
            username: credentials.username,
            password: credentials.password
        }), {headers: this.headers})
        .map((response: Response) => {
            // login successful if there's a jwt token in the response
            let token = response.json() && response.json().token;
            if (token) {
                // set token and username property
                this.token = token;
                this.username = credentials.username;
                
                // store username and jwt token in local storage to keep user logged in between page refreshes
                TokenUtils.removeStoredTokens();
                TokenUtils.storeToken(credentials.rememberMe, JSON.stringify({
                    username: credentials.username,
                    token: token
                }));
                
                // return true to indicate successful login
                return true;
            } else {
                // return false to indicate failed login
                return false;
            }
        });
    }
    
    logout(): void {
        // clear token remove user from local storage to log user out
        this.token = null;
        this.storedRole = null;
        TokenUtils.removeStoredTokens();
    }
    
    getStoredRole(): string{
        return this.storedRole;
    }
    
    setStoredRole(role){
        this.storedRole = role;
    }
    
    getRoleFromToken(): Observable<any> {
        let headers: Headers = TokenUtils.createHeaderWithToken();
        return this.http.get('auth/role', {headers: headers})
        .map(res => {
            return res.json();
        }).catch( () =>{
            this.router.navigate(['/login']);
            return Observable.of(false);
        });
    }
}
