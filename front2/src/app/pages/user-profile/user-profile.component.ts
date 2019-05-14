import { Component, OnInit } from '@angular/core';
import { Globals } from 'src/app/services/globals';
import { UserModel } from 'src/app/model/user.model';
import * as moment from 'moment';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  user: UserModel;
  
  constructor(private globals: Globals) { }

  ngOnInit() {
    this.user = this.globals.userLoggedIn;
  }

  getBirthday() {
    let d: Date = new Date(this.user.birthday);
    return moment().diff(d, 'years');
  }
}
