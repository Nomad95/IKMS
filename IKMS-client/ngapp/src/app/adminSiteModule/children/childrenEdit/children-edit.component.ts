import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { EmployeeService } from "../../../sharedModule/services/employee.service";
import { Employee } from "../../model/employee/employee";
import { EnumProvider } from "../../../commons/util/enum-provider";
import {Message} from "primeng/primeng";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {ChildrenService} from "../../../sharedModule/services/children.service";
import {Child} from "../../menu/model/children/child";

@Component({
  selector: 'child-edit',
  templateUrl: './children-edit.component.html',
  providers: [EnumProvider]
})
export class ChildrenEditComponent implements OnInit{
    constructor(
        private childrenService: ChildrenService,
        private enumProvider: EnumProvider){}
        
    @Input() private childId: number;
    @Input() private isVisible: boolean = false;
    
    @Output() eventClose = new EventEmitter();
    @Output() eventSave = new EventEmitter();
    
    private child: Child = new Child();
    private msgs: Message[] = [];
    private disabilityLevels = EnumProvider.DISABILITY_LEVELS;
    
    ngOnInit(){
        this.childrenService.getChild(this.childId)
            .subscribe( data => {
                this.child = data;
                this.msgs = [];
            }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
        this.disabilityLevels = this.enumProvider.translateToDropdown(this.disabilityLevels);
    }
    
    closeModal(){
        this.isVisible = false;
        this.eventClose.emit(false);
    }
    
    saveData(child){
        this.childrenService.updateChild(child)
        .subscribe( data => {
            this.eventSave.emit(data);
            this.isVisible = false;
            this.msgs = [];
        }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
    }
  
}
