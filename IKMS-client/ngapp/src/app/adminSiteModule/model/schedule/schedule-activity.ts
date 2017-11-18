export class ScheduleActivity {
    id: number;
    title: string;
    comment: string;
    start: string;
    end: string;
    errors: any[];
    classroom: any;
    group: any;
    employees: any[];
    children: any[];
    
    
    constructor() {
        this.id = null;
        this.comment = '';
        this.start = '';
        this.end = '';
        this.errors = [];
        this.classroom = null;
        this.group = null;
        this.employees = [];
        this.children= [];
    }
    
    static fromPrimengEvent(event): ScheduleActivity{
        var scheduleActivity = new ScheduleActivity();
        scheduleActivity.id = event.id;
        scheduleActivity.title = event.title;
        scheduleActivity.comment = event.comment;
        scheduleActivity.start = event.start;
        scheduleActivity.end = event.end;
        scheduleActivity.errors = event.errors;
        scheduleActivity.classroom = event.classroom;
        scheduleActivity.group = event.group;
        scheduleActivity.employees = event.employees;
        scheduleActivity.children= event.children;
        
        return scheduleActivity;
    }
}
