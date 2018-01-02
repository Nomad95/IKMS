import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import {ConfirmationService, MenuItem, Message, TreeNode} from "primeng/primeng";
import {BreadMaker} from "../../../commons/util/bread-maker";
import {GroupService} from "../../../sharedModule/services/group.service";
import {FileUploadService} from "../../../sharedModule/services/file-upload.service";
import * as FileSaver from 'file-saver';
import {EmployeeService} from "../../../sharedModule/services/employee.service";
import {ChildrenService} from "../../../sharedModule/services/children.service";
import {ParentService} from "../../../sharedModule/services/parent.service";
import {Utils} from "../../../commons/util/utils";
import {CommonMessages} from "../../../commons/util/common-messages";

@Component({
  selector: 'didactic-materials-employee',
  templateUrl: './didactic-materials.component.html',
  providers: [ConfirmationService]
})
export class DidacticMaterialsComponent implements OnInit{
    constructor(
        private groupService: GroupService,
        private confirmationService: ConfirmationService,
        private employeeService: EmployeeService,
        private groupSevice: GroupService,
        private parentService: ParentService,
        private childrenService: ChildrenService,
        private router: Router,
        private uploadService: FileUploadService){}
    
    private msgs: Message[] = [];
    private isLoading: boolean = true;
    private items: MenuItem[];
    private nodes: TreeNode[];
    private selectedNode: TreeNode;
    private children = [];
    private parents = [];
    private groups = [];
    private employees = [];
    private trueCheckbox = true;
    private noData = false;
    
    private displayFileAddModal: boolean = false;
    
    ngOnInit(){
        this.isLoading = true;
        this.items = BreadMaker.makeBreadcrumbs("Pliki","Materiały dydaktyczne");
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
        this.uploadService.getFileTree()
            .subscribe(data => {
                this.nodes = data;
                if (data.length == 0) {
                    this.isLoading = false;
                    this.noData = true;
                } else {
                    this.isLoading = false;
                    this.noData = false;
                }
            }, err => {
                console.log(err);
                this.msgs = CommonMessages.getFilesError();
            });
    }
    
    showAddFileModal(){
        this.displayFileAddModal = true;
    }
    
    onGetFile(materialId){
        this.uploadService.getDidacticMaterial(materialId)
        .subscribe( data => {
            console.log(data);
            var blob = new Blob([data.content], { type: 'image/png' });
            var url = window.URL.createObjectURL(blob);
            FileSaver.saveAs(blob, data.filename);
        }, err => {
            this.msgs = CommonMessages.downloadFileError();
        })
    }
    
    handleModalClose($event){
        this.uploadService.getFileTree()
        .subscribe(data => {
            this.nodes = data;
        }, err => console.log(err));
        this.displayFileAddModal = false;
    }
    
    handleOnModalSuccess($event){
        this.uploadService.getFileTree()
        .subscribe(data => {
            this.nodes = data;
        }, err => console.log(err));
        this.displayFileAddModal = false;
    }
    
}