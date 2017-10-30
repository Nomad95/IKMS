export class Utils {
    
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
}