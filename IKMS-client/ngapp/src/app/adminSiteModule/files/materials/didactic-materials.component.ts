import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import {ConfirmationService, MenuItem, Message, TreeNode} from "primeng/primeng";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {BreadMaker} from "../../../commons/util/bread-maker";
import {Group} from "../../../employeeSiteModule/model/group/group";
import {GroupService} from "../../../sharedModule/services/group.service";
import {FileUploadService} from "../../../sharedModule/services/file-upload.service";
import * as FileSaver from 'file-saver';
import {EmployeeService} from "../../../sharedModule/services/employee.service";
import {ChildrenService} from "../../../sharedModule/services/children.service";
import {ParentService} from "../../../sharedModule/services/parent.service";
import {MinimalDto} from "../../model/minimal-dto";
import {Utils} from "../../../commons/util/utils";

@Component({
  selector: 'didactic-materials',
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
        this.nodes = this.nodesStub;
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
                if (data.length == 0){
                    this.noData = true;
                } else {
                    this.noData = false;
                    this.nodes = data;
                }
                this.isLoading = false;
            }, err => {
                console.log(err);
                this.isLoading = false;
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
            console.log(err);
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
    
    nodesStub = [
        {
            "label": "Dokumenty",
            "data": "Documents Folder",
            "expandedIcon": "fa-folder-open",
            "collapsedIcon": "fa-folder",
            "folder": true,
            "children": [{
                "label": "Praca domowa",
                "data": "Work Folder",
                "expandedIcon": "fa-folder-open",
                "collapsedIcon": "fa-folder",
                "children": [{"label": "Zadanie 1.doc", "icon": "fa-file-word-o", "data": "Expenses Document"}, {"label": "Wierszyk.doc", "icon": "fa-file-word-o", "data": "Resume Document"}]
            },
                {
                    "label": "Bajki",
                    "data": "Home Folder",
                    "expandedIcon": "fa-folder-open",
                    "collapsedIcon": "fa-folder",
                    "children": [{"label": "Jaś i Małgoisia.txt", "icon": "fa-file-word-o", "data": "Invoices for this month"}]
                }]
        },
        {
            "label": "Zdjęcia",
            "data": "Pictures Folder",
            "expandedIcon": "fa-folder-open",
            "collapsedIcon": "fa-folder",
            "children": [
                {"label": "Kolorowanka.jpg", "icon": "fa-file-image-o", "data": "Barcelona Photo"},
                {"label": "Abecadło.jpg", "icon": "fa-file-image-o", "data": "PrimeFaces Logo"},
                {"label": "Zapamiętywanie 1.png", "icon": "fa-file-image-o", "data": "PrimeUI Logo"}]
        },
        {
            "label": "Filmy",
            "data": "Movies Folder",
            "expandedIcon": "fa-folder-open",
            "collapsedIcon": "fa-folder",
            "children": [{
                "label": "Gimnastyka",
                "data": "Pacino Movies",
                "children": [{"label": "Ćwiczenie 1", "icon": "fa-file-video-o", "data": "Scarface Movie"}, {"label": "Ćwiczenie 2", "icon": "fa-file-video-o", "data": "Serpico Movie"}]
            },
                {
                    "label": "Bajki",
                    "data": "De Niro Movies",
                    "children": [{"label": "Edukacja poprzez bajki", "icon": "fa-file-video-o", "data": "Kubuś Puchatek"}, {"label": "Kubuś Puchatek", "icon": "fa-file-video-o", "data": "Untouchables Movie"}]
                }]
        },
        {
            "label": "Plany",
            "data": "Movies Folder",
            "expandedIcon": "fa-folder-open",
            "collapsedIcon": "fa-folder",
            "children": [{
                "label": "Grupa Motylki",
                "data": "Pacino Movies",
                "children": [{"label": "Plan", "icon": "fa-file-pdf-o", "data": "Scarface Movie"}]
            },
                {
                    "label": "Grupa Skrzaty",
                    "data": "De Niro Movies",
                    "children": [{"label": "Plan", "icon": "fa-file-pdf-o", "data": "Kubuś Puchatek"}]
                }]
        }
    ]
    
}
