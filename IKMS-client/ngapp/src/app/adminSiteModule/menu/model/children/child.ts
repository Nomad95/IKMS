export class Child{

    id: number;
    personalData: any;
    parent: any;
    diseases: string;//todo: stub
    allergies: string;//todo: stub
    disabilityLevel: string;
    
    constructor() {
        this.id = null;
        this.personalData = {};
        this.parent = {};
        this.diseases = '';
        this.allergies = '';
        this.disabilityLevel = '';
    }
    
}