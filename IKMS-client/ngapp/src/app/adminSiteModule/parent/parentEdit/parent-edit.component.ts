import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {ParentService} from "../../../sharedModule/services/parent.service";
import {Message} from "primeng/primeng";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {Parent} from "../../menu/model/parent/parent";

@Component({
  selector: 'parent-edit',
  templateUrl: './parent-edit.component.html',
  providers: []
})
export class ParentEditComponent implements OnInit {

  constructor(private parentService: ParentService,) {
  }

  @Input() private parentId: number;
  @Input() private isVisible: boolean = false;

  @Output() eventClose = new EventEmitter();
  @Output() eventSave = new EventEmitter();

  private parent: Parent = new Parent();
  private msgs: Message[] = [];

  ngOnInit(): void {
    this.parentService.getParent(this.parentId)
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
    this.parentService.updateParent(parent)
      .subscribe( data => {
        this.eventSave.emit(data);
        this.isVisible = false;
        this.msgs = [];
      }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
  }
}
