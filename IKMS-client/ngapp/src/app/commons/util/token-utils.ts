import {  Headers } from '@angular/http';


export class TokenUtils {

  /**
   * Gets token from local or session browser storage
   * @returns {Object} token obcject (needs to be stringified)
   */
  static getToken(){
    let token = localStorage.getItem('jwtToken');
    if(!token)
      token = sessionStorage.getItem('jwtToken');
    return token;
  }

  /**
   * stores token to either local or session storage
   */
  static storeToken(shouldBeRemembered: boolean, token: any){
    if(token == null)
      return;
    if (shouldBeRemembered == true) {
      localStorage.setItem('jwtToken', token);
    }
    else {
      sessionStorage.setItem('jwtToken', token);
    }
  }

  static removeStoredTokens(){
    sessionStorage.removeItem('jwtToken');
    localStorage.removeItem('jwtToken');
  }

  static getUsernameFromToken(){
    let token = JSON.parse(this.getToken());
    return token && token.username;
  }

  static isLogged(): boolean{
    let token = this.getToken();
    return !!token;
  }

  static createSimpleHeader(): Headers{
    return new Headers({
      'content-type' : 'application/json'});
  }

  /**
   * Creates headers with auth token and content type app json
   * @returns {Headers}
   * TOKEN MAY BE NULL!
   */
  static createHeaderWithToken(): Headers{
    let headers = this.createSimpleHeader();
    let token = JSON.parse(this.getToken());
    let tokenValue = token && token.token;
    headers.append('Auth-Token',tokenValue);
    return headers;
  }
}
