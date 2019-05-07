import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../models/user';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  listUsers(): Observable<User[]> {
    return this.http.get<User[]>('http://localhost:8080/tooeater/api/user');
  }

  addUser(user: User): Observable<any> {
    return this.http.post('http://localhost:8080/tooeater/api/user', user);
  }

  userAuthentication(email,password){
    return this.http.post('http://localhost:8080/tooeater/api/auth', {email, password});
  }

}
