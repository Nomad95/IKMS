import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { EnumProvider } from "../../../commons/util/enum-provider";
import { PersonalData } from "../../model/personalData/personal-data";
import { PersonalDataAdminService } from "../../services/personal-data.service";
import {DateUtils} from "../../../commons/util/date-utils";
import {Message} from "primeng/primeng";
import {ErrorHandler} from "../../../commons/util/error-handler";

@Component({
  selector: 'personal-data-edit',
  templateUrl: './personal-data-edit.component.html',
  providers: [PersonalDataAdminService, EnumProvider]
})
export class PersonalDataEditComponent implements OnInit{
    constructor(
        private personalDataAdminService: PersonalDataAdminService,
        private enumProvider: EnumProvider){}

    @Input() private personalDataId: number;
    @Input() private isVisible: boolean = false;

    @Output() eventClose = new EventEmitter();
    @Output() eventSave = new EventEmitter();

    private personalData: PersonalData = new PersonalData();
    private genders = EnumProvider.GENDERS;
    private maxDate = new Date();
    private msgs: Message[] = [];

    ngOnInit(){
        this.personalDataAdminService.getPersonalData(this.personalDataId)
            .subscribe( data => {
                console.log(data);
                this.personalData = data;
                this.msgs = [];
            }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
        this.genders = this.enumProvider.translateToDropdown(this.genders);
    }

    closeModal(){
        this.isVisible = false;
        this.eventClose.emit(false);
    }

    saveData(personalData){
        this.personalDataAdminService.updatePersonalData(personalData)
        .subscribe( data => {
            this.eventSave.emit(data);
            this.isVisible = false;
            this.msgs = [];
        }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
    }

    onDateSelected(event){
        this.personalData.dateOfBirth = DateUtils.formatDate(event);
    }

}
