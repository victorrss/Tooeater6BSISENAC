import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http'; import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { UserProfileComponent } from '../../pages/user-profile/user-profile.component';
import { TooeatsComponent } from 'src/app/pages/tooeats/tooeats.component';

import { AppLayoutRoutes } from './app-layout.routing';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ComponentsModule } from 'src/app/components/components.module';
import { ClipboardModule } from 'ngx-clipboard';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(AppLayoutRoutes),
    FormsModule,
    HttpClientModule,
    NgbModule,
    ClipboardModule,
    ComponentsModule,
  ],
  declarations: [
    TooeatsComponent,
    UserProfileComponent,
  ],
  providers: [
  ]
})

export class AppLayoutModule { }