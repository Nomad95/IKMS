import {WeekActivitiesSchedule} from "./impl/weekActivitiesSchedule";
import {DiaryTodaySchedule} from "./impl/diaryTodaySchedule";

export class ScheduleConfigFactory{
    
    public static getConfig(configName){
        switch (configName){
            case 'WEEK_ACTIVITIES':{
                return new WeekActivitiesSchedule();
            }
            case 'DIARY_TODAY':{
                return new DiaryTodaySchedule();
            }
        }
    }
}