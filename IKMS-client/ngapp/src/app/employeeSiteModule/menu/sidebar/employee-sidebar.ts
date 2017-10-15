import { Component } from '@angular/core';
import { MenuItem } from '../../../../../node_modules/primeng/components/common/api';
import { EmployeeSideMenu } from "../model/employee-side-menu";

@Component({
  selector: 'employee-sidebar',
  templateUrl: './employee-sidebar.html'
})
export class EmployeeSidebar{
  
    private items: MenuItem[] = EmployeeSideMenu.items;
  
}
