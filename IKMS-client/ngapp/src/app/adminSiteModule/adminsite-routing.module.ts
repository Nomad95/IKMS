import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminSiteComponent } from './adminsite.component';
import { EmployeeListComponent } from "./employee/employeeList/employee-list.component";
import { EmployeeDetailComponent } from "./employee/employeeDetail/employee-detail.component";

const routes: Routes = [
  { path: '',
    component: AdminSiteComponent,
    children: [
        {
          path: 'employee', component: EmployeeListComponent
        },
        {
          path: 'employee/:id', component: EmployeeDetailComponent
        }
    ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminSiteRoutingModule { }
