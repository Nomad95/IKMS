import {Injectable} from "@angular/core";
import * as enumTranslations from '../../../assets/json/enums.json';

/**
 * Class for translating enums to PrimeNg objects or other needed objects.
 * For inline pipe translation see class: ngapp/src/app/commons/pipes/enum-translate.ts
 */
@Injectable()
export class EnumProvider{
    constructor(){};

    static YES_NO = [true,false];
    static EMPLOYEE_ROLES = ['BABYSITTER','SPEECH_TERAPIST'];
    static GENDERS = ['MAN','WOMAN'];
    static ADDRESS_TYPES = ['CORRESPONDENCE_ADDRESS','ADDRESS','REGISTERED_ADDRESS'];
    static DISABILITY_LEVELS = ['NONE','MILD','MODERATE','CONSIDERABLE'];
    static ROLES = ['ROLE_ADMIN','ROLE_EMPLOYEE','ROLE_PARENT'];
    static WEEK_DAYS_LONG_PL = ['Niedziela', 'Poniedziałek', 'Wtorek', 'Środa', 'Czwartek', 'Piątek', 'Sobota'];
    static WEEK_DAYS_SHORT_PL = ['Nie', 'Pon', 'Wt', 'Śr', 'Czw', 'Pt', 'Sob'];
    static PRIORITY = ['INFORMATION','IMPORTANT'];
    static MONTH_NAMES_SHORT_PL = ['Sty', 'Lut', 'Mar', 'Kwi', 'Maj', 'Cze',
        'Lip', 'Sie', 'Wrz', 'Paź', 'Lis', 'Gru'];
    static MONTH_NAMES_PL = ['Styczeń', 'Luty', 'Marzec', 'Kwiecień', 'Maj', 'Czerwiec', 'Lipiec',
        'Sierpień', 'Wrzesień', 'Październik', 'Listopad', 'Grudzień'];

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
