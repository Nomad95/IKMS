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
                        path: 'child/:id', component: ChildrenDetailComponent
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
