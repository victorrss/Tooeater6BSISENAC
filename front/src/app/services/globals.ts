import { Injectable } from '@angular/core';

import { ToastrService } from 'ngx-toastr';

import { UserModel } from '../model/user.model';

import * as moment from 'moment';

@Injectable()
export class Globals {
  uri: string = 'http://localhost:8080/tooeater/api';
  loading: boolean = false;
  userLoggedIn: UserModel;
  msgErrApi: string = 'Desculpe, estamos com problemas técnicos. Por favor, tente novamente mais tarde.';

  constructor(private toastr: ToastrService) {
  
    try {
      this.userLoggedIn = <UserModel>JSON.parse(localStorage.getItem('user'));
    } catch (error) {
      this.userLoggedIn = null;
    }
  }

  getTimeRelative(d) {
    let m = moment;
    m.locale('pt-br')
    return m(d, "YYYY-MM-DDTHH:mm:ssZ").fromNow();
  }

  getSimpleDate(d) {
    let m = moment;
    m.locale('pt-br')
    return m(d, "YYYY-MM-DDTHH:mm:ssZ").format('LLL');
  }

  arrayRemove(arr, value) {
    return arr.filter((el) => {
      return el != value;
    });
  }

  showToast(title, message, typeMsg) {
    let options: any = {
      disableTimeOut: false,
      closeButton: true,
      progressBar: true,
      progressAnimation: 'decreasing',
    }
    switch (typeMsg) {
      case 'success':
        this.toastr.success(message, title, options);
        break;
      case 'error':
        this.toastr.error(message, title, options);
        break;
      default:
        break;
    }

  }
}