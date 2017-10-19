export class ParentGeneral{
  id: number;
  name: string;
  surname: string;
  user: any;
  personalData: any;

  constructor(){
    this.id = -1;
    this.name = '';
    this.surname = '';
    this.user = {};
    this.personalData = {};
  }
}
