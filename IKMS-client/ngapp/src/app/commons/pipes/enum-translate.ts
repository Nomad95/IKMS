import { Pipe, PipeTransform } from "@angular/core";
import * as enumTranslations from '../../../assets/json/enums.json';

@Pipe({name: 'translateEnum'})
export class EnumTranslatePipe implements PipeTransform{
    
    transform(value: any, ...args: any[]): any {
        let result = (<any>enumTranslations[value]);
        if(result){
            return result;
        } return value;
    }

}