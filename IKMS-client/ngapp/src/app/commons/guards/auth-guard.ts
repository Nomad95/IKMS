import { Injectable }       from '@angular/core';
import {
    CanActivate, Router, ActivatedRouteSnapshot,
    RouterStateSnapshot, CanActivateChild } from '@angular/router';
import {LoginService} from "../../loginModule/service/login.service";
import {Observable} from "rxjs/Observable";
import {TokenUtils} from "../util/token-utils";

@Injectable()
export class AuthGuard implements CanActivate, CanActivateChild {
    constructor(
            private loginService: LoginService,
            private router: Router
    ){};
    
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean |  Observable<boolean> | Promise<boolean>{
        return this.checkAuth(route.data['role'], state.url, route.url);
    }
    
    canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | Observable<boolean> | Promise<boolean> {
        return this.checkAuth(childRoute.data['role'], state.url, childRoute.url);
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
            //this.router.navigate([prevUrl]);//todo: dont work
            this.router.navigate(['/login']);
            this.loginService.setStoredRole(null);
            return false;
        }
    
        let isLogged = TokenUtils.isLogged();
        if (isLogged) {
            return true;
        } else {
            this.router.navigate(['/login']);
        }
        return false;
    }
}