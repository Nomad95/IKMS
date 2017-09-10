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
}
