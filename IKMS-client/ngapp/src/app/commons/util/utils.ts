export class Utils {
    
    static minimalToDropdown(values): any[]{
        let result = [];
        
        for (let item of values){
            result.push({label: item.value, value: item.id});
        }
        return result;
    }
}