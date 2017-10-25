import { User } from "../user/user";

export class PersonalData {
  
   id: number;
   user: any;
   employeeRole: string;
   name: string;
   secondaryName: string;
   pesel: string;
   dateOfBirth: Date;
   placeOfBirth: string;
   familyName: string;
   mothersMaidenName: string;
   fathersName: string;
   mothersName: string;
   gender: string;
   nationality: string;
   contactNumber: string;
   faxNumber: string;
   surname: string;
  
  
  constructor(){
      this.id = -1;
      this.employeeRole = '';
      this.name = '';
      this.surname = '';
      this.user = new User();
      this.secondaryName = '';
      this.pesel = '';
      this.dateOfBirth = null;
      this.placeOfBirth = '';
      this.familyName = '';
      this.mothersMaidenName = '';
      this.fathersName = '';
      this.mothersName = '';
      this.gender = '';
      this.nationality = '';
      this.contactNumber = '';
      this.faxNumber = '';
  }
}
