import {Http} from "@angular/http";
import {Injectable} from "@angular/core";
import * as enumTranslations from '../../../assets/json/enums.json';

/**
 * Class for translating enums to PrimeNg objects or other needed objects.
 * For inline pipe translation see class: ngapp/src/app/commons/pipes/enum-translate.ts
 */
@Injectable()
export class EnumProvider{
    constructor(private http: Http){};
    
    static EMPLOYEE_ROLES = ['BABYSITTER','SPEECH_TERAPIST'];
    static GENDERS = ['MAN','WOMAN'];
    static ADDRESS_TYPES = ['CORRESPONDENCE_ADDRESS','ADDRESS','REGISTERED_ADDRESS'];
    static DISABILITY_LEVELS = ['NONE','MILD','MODERATE','CONSIDERABLE'];
    
    translateToDropdown(values): any[]{
        let result = [];
    
         for (let item of values){
             result.push({label: (<any>enumTranslations[item]), value: item});
         }
         return result;
    }
    
    translateOne(value){
        let result = (<any>enumTranslations[value]);
        if(result){
            return result;
        } return value;
    }
}