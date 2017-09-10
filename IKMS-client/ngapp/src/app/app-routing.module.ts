import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  { path: 'login', loadChildren: './loginModule/login.module#LoginModule' },
  { path: 'parent', loadChildren: './parentSiteModule/parentsite.module#ParentSiteModule' },
  { path: 'admin', loadChildren: './adminSiteModule/adminsite.module#AdminSiteModule' },
  { path: '**', redirectTo: 'login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
