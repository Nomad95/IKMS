export class PresenceDto {
    child: any;
    wasPresent: boolean;
    checkingPerson: any;
    checkingTime: any;
    
    constructor() {
        this.child = null;
        this.wasPresent = false;
        this.checkingPerson = null;
        this.checkingTime = null;
    }
}