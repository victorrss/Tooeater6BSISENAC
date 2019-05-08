import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserComponent } from './views/user/user.component';
import { HttpClientModule } from '@angular/common/http';
import { FollowerComponent } from './views/follower/follower.component';
import { LikeComponent } from './views/like/like.component';
import { TooeatComponent } from './views/tooeat/tooeat.component';
import { CommentComponent } from './views/comment/comment.component';
import { SignUpComponent } from './views/user/sign-up/sign-up.component';
import { FormsModule } from '@angular/forms';
import { SignInComponent } from './views/user/sign-in/sign-in.component';
import { HomeComponent } from './home/home.component';
import { RouterModule } from '@angular/router';
import { appRoutes } from './routes';
import { JwtModule } from '@auth0/angular-jwt';

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    FollowerComponent,
    LikeComponent,
    TooeatComponent,
    CommentComponent,
    SignUpComponent,
    SignInComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(appRoutes),
    JwtModule.forRoot({
      config: {
        tokenGetter: function  tokenGetter() {
             return     localStorage.getItem('access_token');},
        whitelistedDomains: ['localhost:8080'],
        blacklistedRoutes: ['http://localhost:8080/tooeater/api/auth']
      }
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
