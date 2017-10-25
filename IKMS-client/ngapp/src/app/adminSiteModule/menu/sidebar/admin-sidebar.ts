import { Component } from '@angular/core';
import { MenuItem } from '../../../../../node_modules/primeng/components/common/api';
import { AdminSideMenu } from "../../model/admin-side-menu";

@Component({
  selector: 'admin-sidebar',
  templateUrl: './admin-sidebar.html'
})
export class AdminSidebar{

    private items: MenuItem[] = AdminSideMenu.items;

}
