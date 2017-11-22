import {ScheduleActivity} from "../../adminSiteModule/model/schedule/schedule-activity";
export class Utils {
    
    static polishLocale = {
        dayNames: ["Niedziela", "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota"],
        dayNamesShort: ["Nie", "Pon", "Wto", "Śr", "Czw", "Pi", "Sob"],
        dayNamesMin: ["Ni","Pn","Wt","Śr","Cz","Pi","So"],
        monthNames: [ "Styczeń","Luty","Marzec","Kwiecień","Maj","Czerwiec","Lipiec","Sierpień","Wrzesień","Październik","Listopad","Grudzień" ],
        monthNamesShort: [ "Sty", "Lut", "Mar", "Kwi", "Maj", "Cze","Lip", "Sie", "Wrz", "Paź", "Lis", "Gru" ],
        today: 'Dziś',
        clear: 'Wyczyść'
    };
    
    static minimalToDropdown(values): any[]{
        let result = [];
        
        for (let item of values){
            result.push({label: item.value, value: item.id});
        }
        return result;
    }
    
    static minimalToDropdownMinimal(values): any[]{
        let result = [];
        
        for (let item of values){
            result.push({label: item.value, value: {id: item.id, value: item.value}});
        }
        return result;
    }
    
    static minimalToIdList(values): any[]{
        let result = [];
        
        for (let child of values){
            result.push(child.id);
        }
        return result;
    }
    
    static deleteActivityUnwantedFields(activityList){
        for( let i=0; i< activityList.length; i++ ){
            activityList[i] = ScheduleActivity.fromPrimengEvent(activityList[i]);
        }
        return activityList;
    }
}