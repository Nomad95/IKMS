import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AdminSiteComponent} from './adminsite.component';
import {EmployeeListComponent} from "./employee/employeeList/employee-list.component";
import {EmployeeDetailComponent} from "./employee/employeeDetail/employee-detail.component";
import {AuthGuard} from "../commons/guards/auth-guard";
import {ChildrenListComponent} from "./children/childrenList/children-list.component";
import {ChildrenDetailComponent} from "./children/childrenDetail/children-detail.component";
import {ParentListComponent} from "./parent/parentList/parent-list.component";
import {ParentDetailComponent} from "./parent/parentDetails/parent-detail.component";
import {ChildrenCreateComponent} from "./children/childrenCreate/children-create.component";
import { RegistrationComponent } from "./registration/registration.component";

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
