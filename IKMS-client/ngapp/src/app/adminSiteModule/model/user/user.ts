export class User{
    id: number;
    username: string;
    password: string;
    email: string;
    enabled: boolean;
    createdDate: any;
    lastLogged: any;
    role: string;

  
  constructor() {
    this.id = -1;
    this.username = '';
    this.password = null;
    this.email = '';
    this.enabled = false;
    this.createdDate = {};
    this.lastLogged = {};
    this.role = '';
  }
}
