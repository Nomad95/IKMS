/**
 * Util class for easy error handling.
 * General purpose
 */
export class ErrorHandler {

  public static handleLoginError(error: any){
      if(error.status == '401' || error.status == '423')
          return [{severity:'error', detail: error._body}];
      else {
          console.log("unhandled error: "+ error._body);
          return[];
      }
  }
    
    public static handleGenericServerError(error: any){
        if(error.status == '500')
            return [{severity:'error', detail: 'Wewnętrzny błąd serwera'}];
        else if (error.status == '400')
            return [{severity:'error', detail: 'Wystąpił błąd'}];
        else {
            console.log("unhandled error: "+ error._body);
            return[];
        }
    }
}
