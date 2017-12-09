import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {Message} from "primeng/primeng";
import {EnumProvider} from "../../../commons/util/enum-provider";
import {ScheduleActivity} from "../../../adminSiteModule/model/schedule/schedule-activity";
import {EmployeeService} from "../../../sharedModule/services/employee.service";
import {Utils} from "../../../commons/util/utils";
import {ChildrenService} from "../../../sharedModule/services/children.service";
import {GroupService} from "../../../sharedModule/services/group.service";
import {ClassroomService} from "../../../sharedModule/services/classroom.service";
import {DateUtils} from "../../../commons/util/date-utils";
import {RequestOptions} from "@angular/http";
import {FileUploadService} from "../../../sharedModule/services/file-upload.service";

@Component({
  selector: 'add-file',
  templateUrl: './add-file.component.html',
  providers: [EnumProvider]
})
export class AddFileComponent implements OnInit{
    constructor(
        private employeeService: EmployeeService,
        private childrenService: ChildrenService,
        private groupService: GroupService,
        private enumProvider: EnumProvider,
        private classroomService: ClassroomService,
        private uploadService: FileUploadService){}
    
    @Input() private isVisible: boolean = false;

    @Output() eventClose = new EventEmitter();
    @Output() eventSave = new EventEmitter();
    
    @ViewChild('addFileForm') form;
    
    private msgs: Message[] = [];
    private defaultDate = new Date();
    private minDate = null;
    private maxDate = null;
    private pl = Utils.polishLocale;
    private classTypes = EnumProvider.CLASS_TYPES;
    
    ngOnInit(){
        this.defaultDate.setMinutes(0);
        this.classTypes = this.enumProvider.translateToDropdown(this.classTypes);
    }

    closeModal(){
        this.isVisible = false;
        this.eventClose.emit(false);
    }
    
    onUpload(event){
        let fileList: FileList = event.files;
        console.log(event.files);
        if(fileList.length > 0) {
            let file: File = fileList[0];
            let formData: FormData = new FormData();
            formData.append('file', file, file.name);
            this.uploadService.uploadDidacticMaterial(formData);
            console.log("sent!");
        }
    }
}
