import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminSiteComponent } from "./adminsite.component";
import { AdminSiteRoutingModule } from "./adminsite-routing.module";
import { AdminSidebar } from "./menu/sidebar/sidebar";
import { AdminNavbar } from "./menu/navbar/navbar";

import { PanelMenuModule } from "../../../node_modules/primeng/components/panelmenu/panelmenu";
import { ButtonModule } from "../../../node_modules/primeng/components/button/button";
import { TabViewModule } from "../../../node_modules/primeng/components/tabview/tabview";
import { CodeHighlighterModule } from '../../../node_modules/primeng/components/codehighlighter/codehighlighter';
import { MegaMenuModule } from '../../../node_modules/primeng/components/megamenu/megamenu';

@NgModule({
  imports: [
    CommonModule,
    AdminSiteRoutingModule,
    PanelMenuModule,
    ButtonModule,
    TabViewModule,
    CodeHighlighterModule,
    MegaMenuModule
  ],
  declarations: [
    AdminSiteComponent,
    AdminSidebar,
    AdminNavbar
  ]
})
export class AdminSiteModule { }
