import {IConfig} from "../iConfig";
import {EnumProvider} from "../../../commons/util/enum-provider";

export class WeekActivitiesSchedule implements IConfig{
    constructor(){}
    
    options = {
        dayNamesShort: EnumProvider.WEEK_DAYS_SHORT_PL,
        dayNames: EnumProvider.WEEK_DAYS_LONG_PL,
        columnFormat: 'ddd',
        titleFormat: 'YYYY',
        duration: {
            days: 7
        }
    };
    
    headerConfig = false;
    
    allDaySlot = false;
    
    defaultView = "agendaWeek";
    
    weekends = false;
    
    minTime = "06:00:00";
    
    maxTime = "20:00:00";
    
    height = "auto";
    
    defaultDate = "2017-12-12";
    
    timeFormat = "h:mm";
    
    getOptions(): any {
        return this.options;
    }
    
    getHeaderConfig(): any {
        return this.headerConfig;
    }
    
    getAllDaySlot(): boolean {
        return this.allDaySlot;
    }
    
    getDefaultView(): string {
        return this.defaultView;
    }
    
    getWeekends(): boolean {
        return this.weekends;
    }
    
    getMinTime(): string {
        return this.minTime;
    }
    
    getMaxTime(): string {
        return this.maxTime;
    }
    
    getHeight(): string {
        return this.height;
    }
    
    getDefaultDate(): string {
        return this.defaultDate;
    }
    
    getTimeFormat(): string {
        return this.timeFormat;
    }
    
    private events = [
        {
            title: 'Event',
            start: '2017-12-12T11:00:00',
            end: '2017-12-12T12:00:00',
            editable: true
        }
    ];
}
