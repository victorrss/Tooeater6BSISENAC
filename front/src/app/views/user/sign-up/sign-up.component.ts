import { Component, OnInit } from '@angular/core';
import { User } from '../../../models/user';
import { UserService } from '../../../services/user.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  users: Array<User>;
  user: User;
  userSel = new User(); // Por enquanto.

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
