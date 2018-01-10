import {IConfig} from "../IConfig";
import {EnumProvider} from "../../../commons/util/enum-provider";
import {DateUtils} from "../../../commons/util/date-utils";

export class DiaryTodaySchedule implements IConfig{
    constructor(){}

    options = {
        dayNamesShort: EnumProvider.WEEK_DAYS_SHORT_PL,
        dayNames: EnumProvider.WEEK_DAYS_LONG_PL,
        monthNamesShort: EnumProvider.MONTH_NAMES_SHORT_PL,
        monthNames: EnumProvider.MONTH_NAMES_PL,
        columnFormat: 'dddd D',
        titleFormat: 'MMMM D YYYY',
        eventColor: '#3f51b5'
    };
    
    headerConfig = {
        left:   'title',
        center: '',
        right:  ''
    };
    
    allDaySlot = false;

    defaultView = "agendaDay";

    weekends = true;

    minTime = "05:00:00";

    maxTime = "20:00:00";

    height = "auto";
    
    defaultDate = DateUtils.newISODate();
    
    timeFormat = "H:mm";
    
    nowIndicator = true;

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
    
    getNowIndicator(): boolean {
        return this.nowIndicator;
    }
}
