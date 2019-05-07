import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class JwtService {

  constructor(private httpClient: HttpClient) { }

  login(email: string, password: string) {
    return this.httpClient.post<{ access_token: string }>('http://localhost:8080/tooeater/api/auth', { email, password }).pipe(tap(res => {
      localStorage.setItem('token', res.access_token);
    }))
  }

  logout() {
    localStorage.removeItem('token');
  }

  public get loggedIn(): boolean{
    return localStorage.getItem('token') !==  null;
  }


}
