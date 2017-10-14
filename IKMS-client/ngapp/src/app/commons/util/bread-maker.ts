export class BreadMaker {
    
    static makeBreadcrumbs(... values){
        let items = [];
        for (let value of values){
            items.push({label: value});
        }
        
        return items;
    }
}