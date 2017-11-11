/**
 * Schedule config interface.
 * Implement your own fitting properties with new class with this interface.
 * For more options visit https://fullcalendar.io/docs/
 */
export interface IConfig{
    
    /**
     * A configuration object to define properties of FullCalendar
     * that do not have a corresponding option in primeNG schedule.
     */
    getOptions(): any;
    
    /**
     * Object that defines header content.
     */
    getHeaderConfig(): any;
    
    /**
     * Defines if all day schedule slot is visible
     */
    getAllDaySlot(): boolean;
    
    /**
     * Defines default schedule view (day, agendaWeek etc.)
     */
    getDefaultView(): string;
    
    /**
     * Defines whether to show weekend days or not
     */
    getWeekends(): boolean;
    
    /**
     * Min event time
     */
    getMinTime(): string;
    
    /**
     * Max event time
     */
    getMaxTime(): string;
    
    /**
     * Defines height of the component (e.i. "auto")
     */
    getHeight(): string;
    
    /**
     * Default schedule date (2017-12-12)
     * 11- Pon
     * 12- Wt
     * 13- Sr
     * 14- Czw
     * 15- Pt
     */
    getDefaultDate(): string;
    
    /**
     * Defines event time format (e.i. "h:mm")
     */
    getTimeFormat(): string;
}