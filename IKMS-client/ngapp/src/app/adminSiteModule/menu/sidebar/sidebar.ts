import { Component } from '@angular/core';
import { MenuItem } from '../../../../../node_modules/primeng/components/common/api';
import { SideMenu } from "../model/side-menu";

@Component({
  selector: 'admin-sidebar',
  templateUrl: './sidebar.html'
})
export class AdminSidebar{
  
    private items: MenuItem[] = SideMenu.items;
  
}
