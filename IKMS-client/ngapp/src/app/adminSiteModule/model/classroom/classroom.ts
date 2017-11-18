export class Classroom {
    id: number;
    name: string;
    available: boolean;
    scheduleActivities: any[];
    
    
    constructor() {
        this.id = null;
        this.name = '';
        this.available = true;
        this.scheduleActivities = [];
    }
}
