import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { AuthLayoutComponent } from './layouts/auth-layout/auth-layout.component';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppRoutingModule } from './app.routing';
import { ComponentsModule } from './components/components.module';
import { AuthService } from './services/auth/auth.service';
import { AuthGuardService } from './services/auth/auth-guard.service';
import { AppLayoutComponent } from './layouts/app-layout/app-layout.component';
import { Globals } from './services/globals';
import { ApiService } from './services/api.service';

@NgModule({
  imports: [
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    ComponentsModule,
    RouterModule,
    AppRoutingModule,
    NgbModule,
  ],
  declarations: [
    AppComponent,
    AppLayoutComponent,
    AuthLayoutComponent,
  ],
  providers: [
    AuthGuardService,
    AuthService,
    Globals,
    ApiService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }