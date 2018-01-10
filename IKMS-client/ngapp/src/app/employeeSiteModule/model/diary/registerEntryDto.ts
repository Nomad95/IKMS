export class RegisterEntryDto {
    child: any;
    description: string;
    created: any;
    employee: any;
    registerId: number;
    
    constructor() {
        this.child = null;
        this.description = '';
        this.created = null;
        this.employee = null;
        this.registerId = null;
    }
}