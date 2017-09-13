import { Component } from '@angular/core';
import { MenuItem } from '../../../../../node_modules/primeng/components/common/api';
import { NavbarMenu } from "../model/navbar-menu";

@Component({
  selector: 'admin-navbar',
  templateUrl: './navbar.html'
})
export class AdminNavbar{
  
  private items: MenuItem[] = NavbarMenu.items;
  
}
