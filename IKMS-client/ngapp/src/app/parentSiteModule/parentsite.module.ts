import { HttpModule } from "@angular/http";
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ParentSiteComponent } from "./parentsite.component";
import { ParentSiteRoutingModule } from "./parentsite-routing.module";
import { ParentSidebar } from "./menu/sidebar/parent-sidebar";

import { PanelMenuModule } from "../../../node_modules/primeng/components/panelmenu/panelmenu";
import { ButtonModule } from "../../../node_modules/primeng/components/button/button";
import { TabViewModule } from "../../../node_modules/primeng/components/tabview/tabview";
import { CodeHighlighterModule } from '../../../node_modules/primeng/components/codehighlighter/codehighlighter';
import { MegaMenuModule } from '../../../node_modules/primeng/components/megamenu/megamenu';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";


@NgModule({
    imports: [
        CommonModule,
        HttpModule,
        ParentSiteRoutingModule,
        PanelMenuModule,
        ButtonModule,
        TabViewModule,
        CodeHighlighterModule,
        MegaMenuModule,
        NgbModule.forRoot()
    ],
    declarations: [
        ParentSiteComponent,
        ParentSidebar
    ]
})
export class ParentSiteModule {
}
