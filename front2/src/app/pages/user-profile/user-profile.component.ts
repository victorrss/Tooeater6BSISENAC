import { Component, OnInit } from '@angular/core';
import { Globals } from 'src/app/services/globals';
import { UserModel } from 'src/app/model/user.model';
import * as moment from 'moment';
import { ApiService } from 'src/app/services/api.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  user: UserModel;
  apiSubscription: any;

  constructor(private globals: Globals, private apiSvc: ApiService) { }

  ngOnInit() {
    this.getUser();
    this.user = this.globals.userLoggedIn;
  }

  ngOnDestroy(): void {
    if (this.apiSubscription)
      this.apiSubscription.unsubscribe();
  }

  getBirthday() {
    let d: Date = new Date(this.user.birthday);
    return moment().diff(d, 'years');
  }

  getUser() {
    this.apiSubscription = this.apiSvc.getUserMe().subscribe((result: UserModel) => {
      localStorage.setItem('user', JSON.stringify(result));
      this.globals.userLoggedIn = result;
      this.user = result;
    }, (err) => {
      this.globals.showToast('Oh não!', 'Falha ao consultar os dados de seu usuário', 'error')
    });
  }
}
