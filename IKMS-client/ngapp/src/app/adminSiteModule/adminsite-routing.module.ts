import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminSiteComponent } from './adminsite.component';

const routes: Routes = [
  {
    path: '', component: AdminSiteComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminSiteRoutingModule { }
