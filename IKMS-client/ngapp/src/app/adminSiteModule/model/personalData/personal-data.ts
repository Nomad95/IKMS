export class PersonalData {
  
   id: number;
   user: any;
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
      this.id = null;
      this.name = '';
      this.surname = '';
      this.user = null;
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
