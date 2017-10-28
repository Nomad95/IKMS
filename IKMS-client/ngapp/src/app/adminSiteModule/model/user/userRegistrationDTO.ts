export class UserRegistrationDTO{
    username: string;
    email: string;
    role: string;

  
  constructor() {
    this.username = '';
    this.email = '';
    this.role = '';
  }
}
