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
import {FileFormDataDto} from "../../model/upload/file-form-data-dto";
import {ParentService} from "../../../sharedModule/services/parent.service";
import {CommonMessages} from "../../../commons/util/common-messages";

@Component({
  selector: 'add-file-employee',
  templateUrl: './add-file.component.html',
  providers: [EnumProvider]
})
export class AddFileComponent implements OnInit{
    constructor(
        private employeeService: EmployeeService,
        private childrenService: ChildrenService,
        private groupService: GroupService,
        private enumProvider: EnumProvider,
        private parentService: ParentService,
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
    private fileFormDataDto: FileFormDataDto = new FileFormDataDto();
    private children = [];
    private parents = [];
    private groups = [];
    private employees = [];
    private selectedGroups = [];
    private selectedEmployees = [];
    private selectedParents = [];
    
    ngOnInit(){
        this.defaultDate.setMinutes(0);
        this.classTypes = this.enumProvider.translateToDropdown(this.classTypes);
        this.childrenService.getChildrenMinimal()
        .subscribe(data => {
            this.children = Utils.minimalToDropdown(data);
        });
        this.parentService.getAllMinimal()
        .subscribe(data => {
            this.parents = Utils.minimalToDropdown(data);
        });
        this.employeeService.getEmployeesMinimal()
        .subscribe(data => {
            this.employees = Utils.minimalToDropdown(data);
        });
        this.groupService.getGroupsMinimal()
        .subscribe(data => {
            this.groups = Utils.minimalToDropdown(data);
        });
    }

    closeModal(){
        this.isVisible = false;
        this.eventClose.emit(true);
        this.clearForm();
    }
    
    onUpload(event){
        let fileList: FileList = event.files;
        if(fileList.length > 0) {
            let file: File = fileList[0];
            let formData: FormData = new FormData();
            formData.append('file', file, file.name);
            formData.append('description', this.fileFormDataDto.description);
            formData.append('folder', this.fileFormDataDto.folder);
            formData.append('subfolder', this.fileFormDataDto.subfolder);
            formData.append('selectedParents', JSON.stringify(this.selectedParents));
            formData.append('selectedGroups', JSON.stringify(this.selectedGroups));
            formData.append('selectedEmployees', JSON.stringify(this.selectedEmployees));
            this.uploadService.uploadDidacticMaterialByEmployee(formData)
                .subscribe( data => {
                    this.msgs = CommonMessages.fileUploadSuccess();
                }, err => {
                    this.msgs = CommonMessages.fileUploadError();
                });
            this.fileFormDataDto = new FileFormDataDto();
            this.closeModal();
        }
        this.clearForm();
    }
    
    clearFile(event){
        event.clear();
    }
    
    clearForm(){
        this.selectedEmployees = [];
        this.selectedGroups = [];
        this.selectedParents = [];
    }
}
