export class RegisterDto {
    classessType: string;
    activityStart: any;
    leaderId: number;
    presences: any[];
    entries: any[];
    
    constructor() {
        this.classessType = '';
        this.activityStart = null;
        this.leaderId = null;
        this.presences = [];
        this.entries = [];
    }
}