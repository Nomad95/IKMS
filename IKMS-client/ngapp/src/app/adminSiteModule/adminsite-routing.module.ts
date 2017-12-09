import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AdminSiteComponent} from './adminsite.component';
import {EmployeeListComponent} from "./employee/employeeList/employee-list.component";
import {EmployeeDetailComponent} from "./employee/employeeDetail/employee-detail.component";
import {ChildrenListComponent} from "./children/childrenList/children-list.component";
import {ChildrenDetailComponent} from "./children/childrenDetail/children-detail.component";
import {ParentListComponent} from "./parent/parentList/parent-list.component";
import {ParentDetailComponent} from "./parent/parentDetails/parent-detail.component";
import {ChildrenCreateComponent} from "./children/childrenCreate/children-create.component";
import { RegistrationComponent } from "./registration/registration.component";
import {AuthGuard} from "../commons/guards/auth-guard";
import {GroupListComponent} from "./management/group/group-list.component";
import {GroupDetailComponent} from "./management/group/group-detail.component";
import {GroupListManageComponent} from "./management/group/group-list-manage.component";
import {GroupCreateComponent} from "./management/groupCreate/group-create.component";
import {NotificationComponent} from "../communicationModule/notification/notificiationList/notification-list.component";
import {CollectiveScheduleComponent} from "./schedules/schedule/collective-schedule.component";
import {EmployeeScheduleComponent} from "./schedules/schedule/employee-schedule.component";
import {ChildScheduleComponent} from "./schedules/schedule/child-schedule.component";
import {GroupScheduleComponent} from "./schedules/schedule/group-schedule.component";
import {UserListComponent} from "./user/userList/user-list.component";
import {ClassroomListComponent} from "./management/classroom/classroom-list.component";
import {ClassroomCreateComponent} from "./management/classroom/classroom-create.component";
import {MessageInboxComponent} from "../communicationModule/messagebox/inbox/message-inbox.component";
import {MessageOutboxComponent} from "../communicationModule/messagebox/outbox/message-outbox.component";
import {MessageBoxComponent} from "../communicationModule/messagebox/messagebox.component";
import {DetailsInboxComponent} from "../communicationModule/messagebox/details/details-inbox.component";
import {DetailsOutboxComponent} from "../communicationModule/messagebox/details/details-outbox.component";
import {DidacticMaterialsComponent} from "./files/materials/didactic-materials.component";

const routes: Routes = [
    {
        path: '',
        component: AdminSiteComponent,
        canActivate: [AuthGuard],
        data: {
            role: 'ROLE_ADMIN'
        },
        children: [
            {
                path: '',
                canActivateChild: [AuthGuard],
                children: [
                    {
                        path: 'employee', component: EmployeeListComponent
                    },
                    {
                        path: 'employee/detail/:id', component: EmployeeDetailComponent
                    },
                    {
                        path: 'addUser', component: RegistrationComponent
                    },
                    {
                        path: 'user', component: UserListComponent
                    },
                    {
                        path: 'child', component: ChildrenListComponent
                    },
                    {
                        path: 'child/detail/:id', component: ChildrenDetailComponent
                    },
                    {
                        path: 'child/new', component: ChildrenCreateComponent
                    },
                    {
                        path: 'parent', component: ParentListComponent
                    },
                    {
                        path: 'parent/detail/:id', component: ParentDetailComponent
                    },
                    {
                        path: 'classroom', component: ClassroomListComponent
                    },
                    {
                        path: 'classroom/new', component: ClassroomCreateComponent
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
                        path: 'group/new', component: GroupCreateComponent
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
                    },
                    {
                        path: 'files/didactic', component: DidacticMaterialsComponent
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
export class AdminSiteRoutingModule { }
