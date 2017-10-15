import { Component } from '@angular/core';
import { MenuItem } from '../../../../../node_modules/primeng/components/common/api';
import { ParentSideMenu } from "../model/parent-side-menu";

@Component({
  selector: 'parent-sidebar',
  templateUrl: './parent-sidebar.html'
})
export class ParentSidebar{
  
    private items: MenuItem[] = ParentSideMenu.items;
  
}
