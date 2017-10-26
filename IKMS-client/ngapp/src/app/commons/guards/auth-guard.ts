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
        let currentRole = this.loginService.getStoredRole();
        this.loginService.redirectUrl = nextUrl;
        if(!currentRole) {
            return this.loginService.getRoleFromToken()
            .map(data => {
                currentRole = data.role;
                this.loginService.setStoredRole(currentRole);
                return this.checkRoleAndLogin(role,currentRole,prevUrl);
    
            });
        } else {
            return this.checkRoleAndLogin(role,currentRole,prevUrl);
        }
    }
    
    checkRoleAndLogin(neededRole, currentRole, prevUrl){
        if (neededRole != currentRole) {
            this.router.navigate(prevUrl);
            return false;
        }
    
        let isLogged = TokenUtils.isLogged();
        if (isLogged) {
            return true;
        } else {
            this.router.navigate(prevUrl);
        }
        return false;
    }
}