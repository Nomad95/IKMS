import {MessageService} from "../../../sharedModule/services/message.service";
import {Component, OnInit} from "@angular/core";
import {MessageInternal} from "../model/message";
import {ConfirmationService, Message} from "primeng/primeng";
import {GenericPage} from "../../notification/model/genericPage";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {DateUtils} from "../../../commons/util/date-utils";

@Component({
  selector: 'message-inbox',
  templateUrl: './message-inbox.component.html',
  providers: [MessageService, ConfirmationService]
})
export class MessageInboxComponent implements OnInit{

  private isLoading: boolean = true;
  private pageWithMessages: GenericPage<MessageInternal>;
  private receivedMessages: Array<MessageInternal>;
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
    this.getReceivedMessageByPage(0);
  }

  getReceivedMessageByPage(numberOfPage: number): void{
    this.receivedMessages = [];
    this.indexesOfPage = [];
    this.isLoading = true;
    this.messageService
      .getMyReceivedMessages(numberOfPage)
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
            this.receivedMessages.push(message);
          }
          this.countMyUnreadMessages();
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
    // check
    var countMessage =  this.messagesToDelete.length;
    for(let message of this.messagesToDelete){
      if(countMessage == 1)
        this.messageService
          .deleteMyReceivedMessage(message)
          .subscribe(()=>{
            this.countMyUnreadMessages();
          }, err => {
            this.msgs = ErrorHandler.handleGenericServerError(err);
          });
      else
        this.messageService
          .deleteMyReceivedMessage(message)
          .subscribe();
      countMessage--;
    }
    this.reloadPage();
  }

  getSelectedOptions() {
    this.messagesToDelete = [];
    this.messagesToDelete = this.receivedMessages
      .filter(opt => opt.checked)
      .map(opt => opt.id);
  }

  countMyUnreadMessages():void{
    this.messageService.countMyUnreadMessages().subscribe(
      result => {
       this.messageService.setAmountOfUnreadMessage(result.count)
      },
      err => {
        this.msgs = ErrorHandler.handleGenericServerError(err);
      }
    );
  }
  reloadPage():void{
    window.location.reload();
  }

  paginate(event) {
    this.getReceivedMessageByPage(event.page);
  }

   showDialog() {
    this.display = true;
  }
}
