import {Component, OnInit} from "@angular/core";
import {MessageService} from "../../../sharedModule/services/message.service";
import {ConfirmationService, Message} from "primeng/primeng";
import {GenericPage} from "../../notification/model/genericPage";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {MessageInternal} from "../model/message";
import {DateUtils} from "../../../commons/util/date-utils";

@Component({
  selector: 'message-outbox',
  templateUrl: './message-outbox.component.html',
  providers: [MessageService, ConfirmationService]
})
export class MessageOutboxComponent implements OnInit{

  private isLoading: boolean = true;
  private pageWithMessages: GenericPage<MessageInternal>;
  private sentMessages: Array<MessageInternal>;
  private messagesToDelete = [];
  private indexesOfPage;
  private msgs: Message[] = [];
  private first;
  private currentPage: number;
  private display: boolean;

  constructor(private messageService: MessageService ) {
    this.currentPage = 0;
    this.display = false;

  }

  ngOnInit() {
    this.getSentMessageByPage(0);
  }

  getSentMessageByPage(numberOfPage: number): void{
    this.sentMessages = [];
    this.indexesOfPage = [];
    this.isLoading = true;
    this.messageService
      .getMySentMessages(numberOfPage)
      .subscribe( result => {
          this.pageWithMessages = result;
          for(let message of this.pageWithMessages.content){
            message.checked = false;
            let date = new Date(message.dateOfSend);
            if(date.toDateString() === new Date(Date.now()).toDateString()){
              message.dateOfSend = new Date(message.dateOfSend).toLocaleTimeString();
            } else {
              message.dateOfSend = DateUtils.formatDateTime(date);
            }
            this.sentMessages.push(message);
          }
          this.first = (this.pageWithMessages.number)*this.pageWithMessages.size;
          this.isLoading = false;
        },
        err => {
          this.isLoading = false;
          this.msgs = ErrorHandler.handleGenericServerError(err);
        }
      );
  }

  deleteSelectedMessages():void{
    this.getSelectedOptions();
    for(let message of this.messagesToDelete){
      this.messageService
        .deleteMySentMessage(message)
        .subscribe(()=>{
        }, err => {
          this.msgs = ErrorHandler.handleGenericServerError(err);
        });
    }
    this.reloadPage();
  }

  getSelectedOptions() {
    this.messagesToDelete = [];
    this.messagesToDelete = this.sentMessages
      .filter(opt => opt.checked)
      .map(opt => opt.id);
  }

  reloadPage():void{
    window.location.reload();
  }

  paginate(event) {
    this.getSentMessageByPage(event.page);
  }

  showDialog() {
    this.display = true;
  }
}
