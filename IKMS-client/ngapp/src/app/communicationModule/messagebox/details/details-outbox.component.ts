import {Component, OnInit} from "@angular/core";
import {MessageService} from "../../../sharedModule/services/message.service";
import {MessageInternal} from "../model/message";
import {ActivatedRoute, Router} from "@angular/router";
import {ConfirmationService, Message} from "primeng/primeng";

@Component({
  selector: 'details-outbox',
  templateUrl: './details-outbox.component.html',
  providers: [MessageService, ConfirmationService ]
})
export class DetailsOutboxComponent implements OnInit{

  private isLoading: boolean = true;
  private id: number;
  private sub: any;
  private message: MessageInternal;
  private msgs: Message[] = [];

  constructor(private route: ActivatedRoute,
              private messageService: MessageService) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.id = +params['id']; // (+) converts string 'id' to a number
      this.getMessageDetails();
    });
  }

  getMessageDetails():void{
    this.messageService
      .getSentMessageDetails(this.id)
      .subscribe( result =>{
          this.message = result;
          this.isLoading = false;
        }, err => {
          this.isLoading = false;
        }
      )
  }


}
