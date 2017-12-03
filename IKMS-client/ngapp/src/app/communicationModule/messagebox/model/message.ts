import {User} from "../../../adminSiteModule/model/user/user";

export class MessageInternal{
  id: number;
  title: string;
  messageContents: any;
  dateOfSend: string;
  wasRead: boolean;
  sender: Array<User>;
  recipient: Array<User>;
  checked: boolean;
  recipientUsername:string;
  senderUsername:string;
  recipientFullName:string;
  senderFullName:string;


  constructor() {
    this.id = -1;
    this.title = '';
    this.messageContents ='';
    this.dateOfSend = '';
    this.wasRead = true;
    this.sender = null;
    this.recipient = null;
    this.checked = false;
    this.recipientUsername = '';
    this.senderUsername = '';
    this.recipientFullName = '';
    this.senderFullName = '';
  }
}
