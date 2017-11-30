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
    color: any;
    classesType: string;
    currentErrors: string[]; //list of errors that exist locally
    rendering: string = null;
    
    constructor() {
        this.id = null;
        this.comment = '';
        this.start = '';
        this.end = '';
        this.classesType = '';
        this.errors = [];
        this.classroom = null;
        this.group = null;
        this.employees = [];
        this.children= [];
        this.color = null;
        this.currentErrors = [];
        this.rendering = null;
    }
    
    static fromPrimengEvent(activity): ScheduleActivity{
        var scheduleActivity = new ScheduleActivity();
        scheduleActivity.id = activity.id;
        scheduleActivity.title = activity.title;
        scheduleActivity.comment = activity.comment;
        scheduleActivity.start = activity.start;
        scheduleActivity.end = activity.end;
        scheduleActivity.errors = activity.errors;
        scheduleActivity.classroom = activity.classroom;
        scheduleActivity.group = activity.group;
        scheduleActivity.employees = activity.employees;
        scheduleActivity.children= activity.children;
        scheduleActivity.color = activity.color;
        scheduleActivity.currentErrors = activity.currentErrors;
        scheduleActivity.rendering = activity.rendering;
        scheduleActivity.classesType = activity.classesType;
        
        return scheduleActivity;
    }
    
    static equals(act1, act2){
        if( act1.title != act2.title){
            return false;
        }
        
        if( act1.classesType != act2.classesType){
            return false;
        }
    
        if( act1.classroom != act2.classroom ){
            return false;
        }
    
        if( act1.employees != act2.employees){
            return false;
        }
    
        if( act1.group != act2.group){
            return false;
        }
    
        if( act1.children != act2.children){
            return false;
        }
        
        return true;
    }
}
