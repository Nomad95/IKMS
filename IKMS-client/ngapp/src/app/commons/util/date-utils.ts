export class DateUtils {

    static formatDate(date): any {
        let d = new Date(date),
                month = '' + (d.getMonth() + 1),
                day = '' + d.getDate(),
                year = d.getFullYear();

        if (month.length < 2) month = '0' + month;
        if (day.length < 2) day = '0' + day;

        return [year, month, day].join('-');
    }
    
    /**
     *  format: yyyy-MM-dd HH:mm:ss
     */
    static formatDateTime(date): any {
        let d = new Date(date),
                month = '' + (d.getMonth() + 1),
                day = '' + d.getDate(),
                year = d.getFullYear(),
                hours = d.getHours(),
                minutes = d.getMinutes();
        
        let hoursResult, minutesResult;
        
        if (month.length < 2) month = '0' + month;
        if (day.length < 2) day = '0' + day;
        if (hours < 10) {
            hoursResult = '0' + hours;
        } else hoursResult = hours;
        if (minutes < 10) {
            minutesResult = '0' + minutes;
        } else minutesResult = minutes;
    
        let resultDate = [year, month, day].join('-');
        let resultTime = [hoursResult, minutesResult].join(':');

        return resultDate + ' ' + resultTime;
    }
    
    static toPrimeNgCalendarDate(date){
        return new Date(date);
    }
    
    static newISODate(): any {
        let d = new Date(),
                month = '' + (d.getMonth() + 1),
                day = '' + d.getDate(),
                year = d.getFullYear();
        
        if (month.length < 2) month = '0' + month;
        if (day.length < 2) day = '0' + day;
        
        return [year, month, day].join('-');
    }
    
    /**
     * adds :00 if time is HH:mm
     */
    static addSeconds(dateTime): any{
        var myRegexp = /:(.*)/;
        var match = myRegexp.exec(dateTime);
        
        if(match && match.length == 2 ){
            return dateTime + ':00';
        } else return dateTime;
    }
    
    /**
     * ale fajna funkcja
     */
    static substractSeconds(dateTime): any{
        if (dateTime == null)
            return dateTime;
        if(typeof dateTime === 'string'){
            return dateTime.substr(0, dateTime.lastIndexOf(":"));
        }
        if(dateTime instanceof Array){
            let date =  new (Function.prototype.bind.apply(
                    Date, [null].concat(dateTime)));
            return this.formatDateTime(date);
        }
        if(typeof dateTime === 'object'){
            return dateTime._i.substr(0, dateTime.lastIndexOf(":"));
        }
    
        return dateTime;
    }
    
    static ISODateToServerLocalDateTime(dateTime){
        if (dateTime){
            return dateTime.replace('T',' ');
        } else return dateTime;
    }
    
    static objectToDate(obj){
        if (obj != null && typeof obj === "object"){
            return obj._i;
        } else {
            return obj;
        }
    }
    
    
}
