import {ScheduleActivity} from "../../../employeeSiteModule/model/schedule/schedule-activity";
import {Child} from "../../../employeeSiteModule/model/children/child";

export class ScheduleActivityDiaryDetail {
    activity: ScheduleActivity;
    children: Array<Child>;
    
    constructor(){
        this.activity = new ScheduleActivity();
        this.children = null;
    }
}