import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AdminSiteComponent} from './adminsite.component';
import {EmployeeListComponent} from "./employee/employeeList/employee-list.component";
import {EmployeeDetailComponent} from "./employee/employeeDetail/employee-detail.component";
import {AuthGuard} from "../commons/guards/auth-guard";
import {ParentListComponent} from "./parent/parentList/parent-list.component";
import {ParentDetailComponent} from "./parent/parentDetails/parent-detail.component";

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
                        path: 'employee/:id', component: EmployeeDetailComponent
                    },
                    {
                        path: 'parent', component: ParentListComponent
                    },
                    {
                        path: 'parent/:id', component: ParentDetailComponent
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
export class AdminSiteRoutingModule {
}
