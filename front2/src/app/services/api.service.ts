import { Injectable } from '@angular/core';
import { Globals } from './globals';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class ApiService {
    private globals = new Globals;
    constructor(private http: HttpClient) { }

    public getTooeatFeed() {
        let token = localStorage.getItem('token');
        return this.http.get(this.globals.uri + '/tooeat', this.globals.getHttpOptions(token));
    }
}