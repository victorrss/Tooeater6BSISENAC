import { Injectable } from '@angular/core';
import { Globals } from './globals';
import { HttpClient } from '@angular/common/http';
import { TooeatModel } from '../model/tooeat.model';

@Injectable()
export class ApiService {
    private globals = new Globals;
    constructor(private http: HttpClient) { }

    getTooeatFeed() {
        let token = localStorage.getItem('token');
        return this.http.get(this.globals.uri + '/tooeat', this.globals.getHttpOptions(token));
    }

    postTooeat(t: TooeatModel) {
        let token = localStorage.getItem('token');
        return this.http.post(this.globals.uri + '/tooeat', t, this.globals.getHttpOptions(token));
    }

    deleteTooeat(tooeatId) {
        let token = localStorage.getItem('token');
        return this.http.delete(this.globals.uri + '/tooeat/' + tooeatId, this.globals.getHttpOptions(token));
    }

    getComments(tooeatId) {
        let token = localStorage.getItem('token');
        return this.http.post(this.globals.uri + '/comment/' + tooeatId, this.globals.getHttpOptions(token));
    }
}