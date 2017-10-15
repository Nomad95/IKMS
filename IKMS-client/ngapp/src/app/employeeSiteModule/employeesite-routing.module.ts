import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EmployeeSiteComponent } from './employeesite.component';

const routes: Routes = [
  {
    path: '', component: EmployeeSiteComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmployeeSiteRoutingModule { }
