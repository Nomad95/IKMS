export class DiaryRegistryDto {
    id: number;
    text: string;
    child: any;
    
    constructor(){
        this.id = null;
        this.text = '';
        this.child = {};
    }
}