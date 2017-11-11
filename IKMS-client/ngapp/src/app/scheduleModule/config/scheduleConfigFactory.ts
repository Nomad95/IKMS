import {WeekActivitiesSchedule} from "./impl/weekActivitiesSchedule";

export class ScheduleConfigFactory{
    
    public static getConfig(configName){
        switch (configName){
            case 'WEEK_ACTIVITIES':{
                return new WeekActivitiesSchedule();
            }
        }
    }
}