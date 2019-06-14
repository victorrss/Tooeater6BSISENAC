import { Injectable } from '@angular/core';
import { Globals } from './globals';
import { HttpClient } from '@angular/common/http';
import { TooeatModel } from '../model/tooeat.model';
import { Observable, interval, throwError, of } from 'rxjs';
import { UserModel } from '../model/user.model';
import { AuthModel } from '../model/auth.model';
import { CommentModel } from '../model/comment.model';
import { SearchModel } from '../model/search.model';
import { FollowerModel } from '../model/follower.model';
import { retryWhen, flatMap, delay } from 'rxjs/operators';

@Injectable()
export class ApiService {
  constructor(private http: HttpClient, protected globals: Globals) { }

  // BEGIN - CRUD TOOEAT
  postTooeat(t: TooeatModel) {
    return this.http.post(this.globals.uri + '/tooeat', t);
  }
  getTooeatFeed(): Observable<TooeatModel[]> {
    return <Observable<TooeatModel[]>>(
      this.http.get(this.globals.uri + '/tooeat')
    );
  }
  updateTooeat(t: TooeatModel) {
    return this.http.put(this.globals.uri + '/tooeat', t);
  }
  deleteTooeat(tooeatId: number) {
    return this.http.delete(this.globals.uri + '/tooeat/' + tooeatId);
  }
  // END - CRUD TOOEAT

  // BEGIN - CRUD USER
  postUser(user: UserModel) {
    delete(user.isFollower);
    return <Observable<UserModel>>(
      this.http.post(this.globals.uri + '/user', user)
    );
  }
  getUserMe(): Observable<UserModel> {
    return <Observable<UserModel>>this.http.get(this.globals.uri + '/user/me');
  }
  updateUser(user: UserModel) {
    delete(user.isFollower);
    return this.http.put(this.globals.uri + '/user', user);
  }
  deleteUser() {
    return this.http.delete(this.globals.uri + '/user');
  }
  getSearchUser(term: string): Observable<SearchModel[]> {
    return <Observable<SearchModel[]>>this.http.get(this.globals.uri + '/user/search/' + term);
  }
  getByNickname(nickname: string): Observable<SearchModel> {
    return <Observable<SearchModel>>this.http.get(this.globals.uri + '/user/nickname/' + nickname);
  }
  // END - CRUD USER

  // BEGIN - CRUD COMMENT
  getComments(tooeatId: number) {
    return this.http.get(this.globals.uri + '/comment/' + tooeatId);
  }
  postComment(tooeatId: number, c: CommentModel) {
    return this.http.post(this.globals.uri + '/comment/' + tooeatId, c);
  }
  deleteComment(commentId: number) {
    return this.http.delete(this.globals.uri + '/comment/' + commentId);
  }
  // END - CRUD COMMENT

  // BEGIN - CRUD LIKE
  getLikeDislike(tooeatId: number): Observable<any> {
    return this.http.get(this.globals.uri + '/like/' + tooeatId, { observe: 'response' });
  }
  // END - CRUD LIKE

  // BEGIN - CRUD AUTH
  getAuthToken(body): Observable<AuthModel> {
    return <Observable<AuthModel>>(
      this.http.post(this.globals.uri + '/auth', body)
    );
  }
  // END - CRUD AUTH

  // BEGIN - CRUD FOLLOW
  getFollowers(): Observable<FollowerModel[]> {
    return <Observable<FollowerModel[]>>(
      this.http.get(this.globals.uri + '/follower/followers')
    );
  }

  getFollowing(): Observable<FollowerModel[]> {
    return <Observable<FollowerModel[]>>(
      this.http.get(this.globals.uri + '/follower/following')
    );
  }

  getIsFollower(nickname: string) {
    return this.http.get(this.globals.uri + '/follower/isFollower/' + nickname, { observe: 'response' });
  }

  postFollowerInvite(nickname: string) {
    return this.http.post(this.globals.uri + '/follower/invite/' + nickname, {}, { observe: 'response' });
  }

  // END - CRUD FOLLOW

  http_retry(maxRetry: number = 5, delayMs: number = 2000) {
    return (src: Observable<any>) => src.pipe(
      retryWhen(_ => {
        return interval(delayMs).pipe(
          flatMap(count => count == maxRetry ? throwError("Giving up") : of(count))
        )
      })
    )
  }
}
