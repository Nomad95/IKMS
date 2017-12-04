import {Component, Input, OnInit, ViewChild} from "@angular/core";
import {Message} from "primeng/primeng";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {MessageService} from "../../../sharedModule/services/message.service";
import {NewMessage} from "../model/new-message";

@Component({
  selector: 'sending-message',
  templateUrl: './sending-message.component.html',
  providers: [MessageService]
})
export class SendingMessageComponent implements OnInit{

  private isLoading: boolean;
  private message: NewMessage;
  private msgs: Message[] = [];

  @Input() recipientUsername: string;
  @ViewChild('messageForm') form;

  constructor(private messageService: MessageService){
  }

  ngOnInit(): void {
    this.clearForm();
  }

  sendMessage(message: NewMessage):void{
    this.isLoading = true;
    this.messageService
      .sendMessage(message,this.recipientUsername )
      .subscribe( newMessage => {
        this.message;
        this.isLoading = false;
        this.clearForm();
      }, error2 => {
        this.isLoading = false;
        this.msgs = ErrorHandler.handleGenericServerError(error2);
      })
  }

  clearForm(){
    this.message = new NewMessage();
    this.form.reset();
  }

}