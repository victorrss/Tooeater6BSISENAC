import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Tooeat } from '../models/Tooeat';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TooeatService {
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      // 'Authorization': 'my-auth-token'
    })
  };

  constructor(private http: HttpClient) { }

  listTooeats(): Observable<Tooeat[]> {
    return this.http.get<Tooeat[]>('http://localhost:8080/tooeater/api/tooeat');
  }

  addTooeat(tooeat: Tooeat): Observable<any> {
    return this.http.post('http://localhost:8080/tooeater/api/tooeat', Tooeat, this.httpOptions);
  }

  remove(id: number): Observable<any> {
    return this.http.delete('http://localhost:8080/tooeater/api/tooeat' + id);
  }

  update(tooeat: Tooeat): Observable<any> {
    return this.http.put('http://localhost:8080/tooeater/api/Tooeat', Tooeat);
  }

}
