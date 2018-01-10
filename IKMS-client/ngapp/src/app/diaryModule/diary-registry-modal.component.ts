import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {Message} from "primeng/primeng";
import {DiaryRegistryDto} from "../employeeSiteModule/model/diary/diaryRegistryDto";
import {EnumProvider} from "../commons/util/enum-provider";
import {EmployeeService} from "../sharedModule/services/employee.service";
import {ChildrenService} from "../sharedModule/services/children.service";
import {GroupService} from "../sharedModule/services/group.service";
import {ParentService} from "../sharedModule/services/parent.service";

@Component({
  selector: 'diary-registry-modal',
  templateUrl: './diary-registry-modal.component.html',
  providers: [EnumProvider]
})
export class DiaryRegistryModal implements OnInit{
    constructor(
        private employeeService: EmployeeService,
        private childrenService: ChildrenService,
        private groupService: GroupService,
        private enumProvider: EnumProvider,
        private parentService: ParentService){}
    
    @Input() private isVisible: boolean = false;

    @Output() eventClose = new EventEmitter();
    @Output() eventSave = new EventEmitter();
    
    @ViewChild('addRegistry') form;
    
    private msgs: Message[] = [];
    private diaryRegistryDto: DiaryRegistryDto = new DiaryRegistryDto();
    
    ngOnInit(){
    }

    closeModal(){
        this.isVisible = false;
        this.eventClose.emit(true);
        this.form.reset();
    }
    
    save(){
    }
}
