export class Group {
  id: number;
  name: string;
  createdDate: Date;
  active: boolean;
  groupSize: number;
  employee: any;
  children: any[];

  constructor(){
    this.id = 0;
    this.name = '';
    this.createdDate = null;
    this.active = true;
    this.groupSize = 0;
    this.employee = {};
    this.children = [];
  }

}
