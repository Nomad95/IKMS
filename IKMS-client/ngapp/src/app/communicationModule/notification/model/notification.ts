export class Notification{

  id: number;
  content: string;
  dateOfSend: string;
  wasRead: boolean;
  senderFullName: string;
  checked: boolean;
  priority: any;

  constructor(){
    this.id = -1;
    this.content = '';
    this.dateOfSend = null;
    this.wasRead = null;
    this.senderFullName = '';
    this.checked = false;
    this.priority = null;
  }
}
