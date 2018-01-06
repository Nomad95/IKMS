import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ParentSiteComponent } from './parentsite.component';
import {AuthGuard} from "../commons/guards/auth-guard";
import {NotificationComponent} from "../communicationModule/notification/notificiationList/notification-list.component";
import {MessageBoxComponent} from "../communicationModule/messagebox/messagebox.component";
import {DetailsInboxComponent} from "../communicationModule/messagebox/details/details-inbox.component";
import {DetailsOutboxComponent} from "../communicationModule/messagebox/details/details-outbox.component";
import {DidacticMaterialsComponent} from "./files/didactic/didactic-materials.component";
import {EmployeeListComponent} from "./employee/employeeList/employee-list.component";

const routes: Routes = [
    {
        path: '',
        component: ParentSiteComponent,
        canActivate: [AuthGuard],
        data: {
            role: 'ROLE_PARENT'
        },
        children: [
            {
                path: '',
                canActivateChild: [AuthGuard],
                children: [
                    {
                        path: 'notification', component: NotificationComponent
                    },
                    {
                        path: 'messagebox', component: MessageBoxComponent
                    },
                    {
                        path: 'messagebox/:type', component: MessageBoxComponent
                    },
                    {
                        path: 'messagebox/inbox/details/:id', component: DetailsInboxComponent
                    },
                    {
                        path: 'messagebox/outbox/details/:id', component: DetailsOutboxComponent
                    },
                    {
                        path: 'files/didactic', component: DidacticMaterialsComponent
                    },
                    {
                        path: 'employee', component: EmployeeListComponent
                    }
                ]
            }
        
        ]
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ParentSiteRoutingModule { }
