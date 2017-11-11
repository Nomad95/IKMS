import { Injectable } from '@angular/core';

@Injectable()
export class UtilMethods {
    constructor() {
    }

    /**
    * Takes string and returns first letter Uppercase
    * Example: forces input values to be first ltter upper case
    *      (keypress)="newEventType1.description = firstLetterUpperCase(newEventType1.description)"
    * firstLetterUpperCase method is defined in component as:
    *      firstLetterUpperCase(value: string){
    *      return this.utilMethods.firstLetterUpperCase(value);
    *      }
    * @param value
    * @returns {string}
    */
    public firstLetterUpperCase(value: string): string {
        if (value !== null) {
            if (value.length == 1)
                return value.charAt(0).toUpperCase();
        }
        return value;
    }
}