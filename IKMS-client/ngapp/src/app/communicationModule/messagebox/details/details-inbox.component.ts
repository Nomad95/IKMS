
import {Component, OnInit} from "@angular/core";
import {MessageService} from "../../../sharedModule/services/message.service";
import {MessageInternal} from "../model/message";
import {ActivatedRoute, Router} from "@angular/router";
import {ConfirmationService, Message} from "primeng/primeng";

@Component({
  selector: 'details-inbox',
  templateUrl: './details-inbox.component.html',
  providers: [MessageService, ConfirmationService ]
})
export class DetailsInboxComponent implements OnInit{

  private isLoading: boolean = true;
  private id: number;
  private sub: any;
  private message: MessageInternal;
  private msgs: Message[] = [];
  private display: boolean;

  constructor(private route: ActivatedRoute,private router: Router, private messageService: MessageService) {
    this.display = false;
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      this.id = +params['id']; // (+) converts string 'id' to a number
      this.getMessageDetails();
    });
  }

  getMessageDetails():void{
    this.messageService
      .getReceivedMessageDetails(this.id)
      .subscribe( result =>{
          this.message = result;
          this.isLoading = false;
        }, err => {
        this.isLoading = false;
        }
      )
  }

  showDialog() {
    this.display = true;
  }



}
