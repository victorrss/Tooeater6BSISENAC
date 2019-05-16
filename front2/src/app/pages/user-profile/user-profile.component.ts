import { Component, OnInit } from '@angular/core';
import { Globals } from 'src/app/services/globals';
import { UserModel } from 'src/app/model/user.model';
import * as moment from 'moment';
import { ApiService } from 'src/app/services/api.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  user: UserModel;
  apiSubscription: any;
  today = new Date();
  constructor(private globals: Globals, private apiSvc: ApiService) { }

  ngOnInit() {
    this.getUser();
    this.user = this.globals.userLoggedIn;
  }

  ngOnDestroy(): void {
    if (this.apiSubscription)
      this.apiSubscription.unsubscribe();
  }

  onSubmit() {
    let userUpdate = this.user;
    delete(userUpdate.tooeats)
    delete(userUpdate.following)
    delete(userUpdate.followers)
    delete(userUpdate.photo)
    delete(userUpdate.email)
    delete(userUpdate.createdAt)
    delete(userUpdate.updateAt)

    this.apiSubscription = this.apiSvc.updateUser(this.user).subscribe(
      () => {
        this.globals.showToast('Aí sim!', 'Usuário atualizado com sucesso!', 'success')
        this.getUser()
      },
      (err: HttpErrorResponse) => this.globals.showToast('Oh não!', (err.status == 406) ? err.error.message : this.globals.msgErrApi, 'error')
    );
  }

  getBirthday() {
    let d: Date = new Date(this.user.birthday);
    let age: number = moment().diff(d, 'years');
    return !Number.isInteger(age) || age > 130 ? '' : age
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
