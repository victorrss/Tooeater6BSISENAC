import { Component, OnInit } from '@angular/core';
import { User } from '../models/user';
import { UserService } from '../services/user.service'

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  users: Array<User>;
  user: User;
  userSel = null;

  constructor(private userService: UserService) { }

  ngOnInit() {
  }

  OnSelect(user: User): void {
    this.userSel = user;
  }

  newUser(): void {
    this.userSel = new User();
  }

  addUser(): void {
    this.userService.addUser(this.userSel).subscribe(
      () => {
        alert('Usuário inserido com sucesso!!!');
      }
    );
  }

  listUsers(): void {
    this.userService.listUsers().subscribe(
      userFromBackend => {
        this.users = userFromBackend;
      }
    );
  }

}
