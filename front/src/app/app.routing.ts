import { NgModule } from '@angular/core';
import { CommonModule, } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { Routes, RouterModule } from '@angular/router';

import { AuthLayoutComponent } from './layouts/auth-layout/auth-layout.component';
import { AuthGuardService as AuthGuard } from './services/auth/auth-guard.service';
import { AppLayoutComponent } from './layouts/app-layout/app-layout.component';
const routes: Routes = [
  {
    path: '',
    redirectTo: 'tooeats',
    pathMatch: 'full'
  }, {
    path: '',
    component: AppLayoutComponent,
    children: [
      {
        path: '',
        loadChildren: './layouts/app-layout/app-layout.module#AppLayoutModule'
      }
    ],
    canActivate: [AuthGuard]

  }, {
    path: '',
    component: AuthLayoutComponent,
    children: [
      {
        path: '',
        loadChildren: './layouts/auth-layout/auth-layout.module#AuthLayoutModule'
      }
    ]
  }, {
    path: '**',
    redirectTo: 'login'
  }
];

@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule.forRoot(routes)
  ],
  exports: [],
})
export class AppRoutingModule { }
