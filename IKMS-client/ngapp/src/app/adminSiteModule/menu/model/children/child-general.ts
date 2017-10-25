export class ChildGeneral{
    
    id: number;
    name: string;
    surname: string;
    personalData: any;
    parent: any;
    
    constructor(){
        this.id = -1;
        this.name = '';
        this.surname = '';
        this.parent = {};
        this.personalData = {};
    }
}