import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import * as moment from 'moment';

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

  getTimeRelative(d) {
    let m = moment;
    m.locale('pt-br')
    return m(d, "YYYY-MM-DDTHH:mm:ssZ").fromNow();
    //  YYYY-MM-DDTHH:mm:ss.SSS
  }

  getSimpleDate(d) {
    let m = moment;
    m.locale('pt-br')
    return m(d, "YYYY-MM-DDTHH:mm:ssZ").format('LLL');
    //  YYYY-MM-DDTHH:mm:ss.SSS
  }

  arrayRemove(arr, value) {
    return arr.filter((ele) => {
      return ele != value;
    });
  }
}