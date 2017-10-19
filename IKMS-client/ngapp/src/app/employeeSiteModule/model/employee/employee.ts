export class Employee {
  
   id: number;
   employeeRole: string;
   nip: string;
   personalData: any;
  
  constructor(){
      this.id = -1;
      this.employeeRole = '';
      this.nip = '';
      this.personalData = {};
  }
}
