import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class JwtService {
  uri = 'http://localhost:8080/tooeater/api'
  constructor(private httpClient: HttpClient) { }

  login(username: string, password: string) {
    return this.httpClient.post<{ token: string }>(this.uri + '/auth', { username, password })
      .pipe(tap((res: any) => {
        localStorage.setItem('token', res.token);
      }))
  }

  logout() {
    localStorage.removeItem('token');
  }

  public get loggedIn(): boolean {
    return localStorage.getItem('token') !== null;
  }


}
