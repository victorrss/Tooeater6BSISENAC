import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';

@Injectable()
export class Globals {
  uri: string = 'http://localhost:8080/tooeater/api';

  getHttpOptions(token: string = null) {
    if (token == null) {
      return {
        headers: new HttpHeaders({
          'Content-Type': 'application/json'
        })
      }
    } else {
      return {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + token
        })
      }
    }
  }
}