import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {ParentAdminService} from "../../services/parent-admin.service";
import {Message} from "primeng/primeng";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {Parent} from "../../menu/model/parent/parent";

@Component({
  selector: 'parent-edit',
  templateUrl: './parent-edit.component.html',
  providers: [ParentAdminService]
})
export class ParentEditComponent implements OnInit {

  constructor(private parentAdminService: ParentAdminService,) {
  }

  @Input() private parentId: number;
  @Input() private isVisible: boolean = false;

  @Output() eventClose = new EventEmitter();
  @Output() eventSave = new EventEmitter();

  private parent: Parent = new Parent();
  private msgs: Message[] = [];

  ngOnInit(): void {
    this.parentAdminService.getParent(this.parentId)
      .subscribe( data => {
        this.parent = data;
        this.msgs = [];
      }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
  }

  closeModal(){
    this.isVisible = false;
    this.eventClose.emit(false);
  }

  saveData(parent){
    this.parentAdminService.updateParent(parent)
      .subscribe( data => {
        this.eventSave.emit(data);
        this.isVisible = false;
        this.msgs = [];
      }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
  }
}
