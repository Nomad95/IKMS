import { Injectable }       from '@angular/core';
import {
    CanActivate, Router,
    ActivatedRouteSnapshot,
    RouterStateSnapshot, CanActivateChild
}                           from '@angular/router';
import {LoginService} from "../../loginModule/service/login.service";
import {TokenUtils} from "../util/token-utils";
import {Observable} from "rxjs/Observable";

@Injectable()
export class AuthGuard implements CanActivate, CanActivateChild {
    
    constructor(
            private loginService: LoginService,
            private router: Router
    ){};
    
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean |  Observable<boolean> | Promise<boolean>{
        return this.checkAuth(route.data['role'], route.url, state.url);
    }
    
    canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Observable<boolean> | Promise<boolean> {
        return this.checkAuth(childRoute.data['role'], childRoute.url, state.url);
    }
    
    checkAuth(role, prevUrl, nextUrl){
        let currentRole = null;
        this.loginService.redirectUrl = nextUrl;
        return this.loginService.getRole()
            .map(data =>{
                currentRole = data.role;
    
                if(role != currentRole){
                    this.router.navigate(prevUrl);
                    return false;
                }
    
                let isLogged = TokenUtils.isLogged();
                if(isLogged){
                    return true;
                } else {
                    this.router.navigate(prevUrl);
                }
                return false;
            });
    }
}