import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EmployeeSiteComponent } from './employeesite.component';
import { ChildrenListComponent} from "./children/childrenList/children-list.component";
import { ChildrenDetailComponent} from "./children/childrenDetail/children-detail.component";
import { AuthGuard} from "../commons/guards/auth-guard";
import {GroupScheduleComponent} from "./schedules/schedule/group-schedule.component";
import {ChildScheduleComponent} from "./schedules/schedule/child-schedule.component";
import {CollectiveScheduleComponent} from "./schedules/schedule/collective-schedule.component";
import {EmployeeScheduleComponent} from "./schedules/schedule/employee-schedule.component";
import {EmployeeListComponent} from "./employee/employeeList/employee-list.component";
import {ParentDetailComponent} from "./parent/parentDetails/parent-detail.component";
import {ParentListComponent} from "./parent/parentList/parent-list.component";
import {GroupListComponent} from "./management/group/group-list.component";
import {GroupDetailComponent} from "./management/group/group-detail.component";
import {GroupListManageComponent} from "./management/group/group-list-manage.component";
import {NotificationComponent} from "../communicationModule/notification/notificiationList/notification-list.component";
import {MessageBoxComponent} from "../communicationModule/messagebox/messagebox.component";
import {DetailsInboxComponent} from "../communicationModule/messagebox/details/details-inbox.component";
import {DetailsOutboxComponent} from "../communicationModule/messagebox/details/details-outbox.component";

const routes: Routes = [
    {
        path: '',
        component: EmployeeSiteComponent,
        canActivate: [AuthGuard],
        data: {
            role: 'ROLE_EMPLOYEE'
        },
        children: [
            {
                path: '',
                canActivateChild: [AuthGuard],
                children: [
                    {
                        path: 'child', component: ChildrenListComponent
                    },
                    {
                        path: 'child/detail/:id', component: ChildrenDetailComponent
                    },
                    {
                        path: 'employee', component: EmployeeListComponent
                    },
                    {
                        path: 'parent', component: ParentListComponent
                    },
                    {
                        path: 'parent/detail/:id', component: ParentDetailComponent
                    },
                    {
                        path: 'group', component: GroupListComponent
                    },
                    {
                        path: 'group/detail/:id', component: GroupDetailComponent
                    },
                    {
                        path: 'group/manage/childList/:groupId', component: GroupListManageComponent
                    },
                    {
                        path: 'schedule/collective', component: CollectiveScheduleComponent
                    },
                    {
                        path: 'schedule/employee', component: EmployeeScheduleComponent
                    },
                    {
                        path: 'schedule/child', component: ChildScheduleComponent
                    },
                    {
                        path: 'schedule/group', component: GroupScheduleComponent
                    },
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
export class EmployeeSiteRoutingModule { }
