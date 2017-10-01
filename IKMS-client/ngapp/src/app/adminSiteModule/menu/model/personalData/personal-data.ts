export class PersonalData {
  
   id: number;
   user: any;
   employeeRole: string;
   nip: string;
   name: string;
   secondaryName: string;
   pesel: string;
   dateOfBirth: any;
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
      this.nip = '';
      this.name = '';
      this.surname = '';
      this.user = {};
      this.secondaryName = '';
      this.pesel = '';
      this.dateOfBirth = {};
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
