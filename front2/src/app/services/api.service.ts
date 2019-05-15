import { Injectable } from '@angular/core';
import { Globals } from './globals';
import { HttpClient } from '@angular/common/http';
import { TooeatModel } from '../model/tooeat.model';
import { Observable } from 'rxjs';
import { UserModel } from '../model/user.model';
import { AuthModel } from '../model/auth.model';

@Injectable()
export class ApiService {
    constructor(private http: HttpClient, protected globals: Globals) { }

    // BEGIN - CRUD TOOEAT
    postTooeat(t: TooeatModel) {
        return this.http.post(this.globals.uri + '/tooeat', t);
    }
    getTooeatFeed(): Observable<TooeatModel[]> {
        return <Observable<TooeatModel[]>>this.http.get(this.globals.uri + '/tooeat');
    }
    updateTooeat(t: TooeatModel) {
        return this.http.put(this.globals.uri + '/tooeat', t);
    }
    deleteTooeat(tooeatId: number) {
        return this.http.delete(this.globals.uri + '/tooeat/' + tooeatId);
    }
    // END - CRUD TOOEAT


    // BEGIN - CRUD USER
    getUserMe(): Observable<UserModel> {
        return <Observable<UserModel>>this.http.get(this.globals.uri + '/user/me');
    }
    // END - CRUD USER


    // BEGIN - CRUD COMMENT
    getComments(tooeatId: number) {
        return this.http.get(this.globals.uri + '/comment/' + tooeatId);
    }
    // END - CRUD COMMENT


    // BEGIN - CRUD AUTH
    getAuthToken(body):Observable<AuthModel> {
        return <Observable<AuthModel>>this.http.post(this.globals.uri + '/auth', body);
    }
    // END - CRUD AUTH
}