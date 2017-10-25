import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EmployeeSiteComponent } from './employeesite.component';
import {AuthGuard} from "../commons/guards/auth-guard";
import {ChildrenListComponent} from "./children/childrenList/children-list.component";
import {ChildrenDetailComponent} from "./children/childrenDetail/children-detail.component";

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
