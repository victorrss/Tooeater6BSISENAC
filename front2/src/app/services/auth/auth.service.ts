import { Injectable } from '@angular/core';
import { Globals } from '../globals';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { JwtService } from '../jwt.service';
import { Router } from '@angular/router';

@Injectable()
export class AuthService {
    private jwtService = new JwtService();

    private globals = new Globals;
    constructor(private http: HttpClient, private router: Router) { }

    public isAuthenticated(): boolean {
        const token = localStorage.getItem('token');
        return this.jwtService.isValid(token);
    }

    public getToken(body) {
        return this.http.post(this.globals.uri + '/auth', body, this.globals.getHttpOptions());
    }

    public logout() {
        localStorage.setItem('token', null);
        this.router.navigate(['/login']);
    }
}