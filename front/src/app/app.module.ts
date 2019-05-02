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

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    FollowerComponent,
    LikeComponent,
    TooeatComponent,
    CommentComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
