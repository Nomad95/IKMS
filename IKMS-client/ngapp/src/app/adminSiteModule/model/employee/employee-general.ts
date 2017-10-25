export class EmployeeGeneral {
  
   id: number;
   employeeRole: string;
   nip: string;
   name: string;
   surname: string;
   user: any;
   personalData: any;
  
  constructor(){
      this.id = -1;
      this.employeeRole = '';
      this.nip = '';
      this.name = '';
      this.surname = '';
      this.user = {};
      this.personalData = {};
  }
}
