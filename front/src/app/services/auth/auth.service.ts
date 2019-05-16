import { Injectable } from '@angular/core';
import { Globals } from '../globals';
import { HttpClient } from '@angular/common/http';
import { JwtService } from '../jwt.service';
import { Router } from '@angular/router';

@Injectable()
export class AuthService {
    private jwtService = new JwtService();

    constructor(private http: HttpClient, private router: Router, protected globals: Globals) { }

    public isAuthenticated(): boolean {
        const token = localStorage.getItem('token');
        return this.jwtService.isValid(token);
    }

    public logout() {
        localStorage.setItem('token', null);
        localStorage.setItem('user', null);
        this.globals.userLoggedIn = null;
        this.router.navigate(['/login']);
    }
}