import { Component, OnInit, OnDestroy } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { UserModel } from 'src/app/model/user.model';
import { AuthModel } from 'src/app/model/auth.model';
import { Globals } from 'src/app/services/globals';
import { ApiService } from 'src/app/services/api.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy {
  form = {
    username: '',
    password: ''
  }
  errorMsg: string = null;
  success: boolean = false;
  loading = false;
  constructor(private authService: AuthService, private router: Router, private globals: Globals, private apiSvc: ApiService) { }

  ngOnInit() {
    if (this.authService.isAuthenticated())
      this.router.navigate(['../tooeats']);
  }
  ngOnDestroy() { }

  onSubmit() {
    this.loading = true;
    this.errorMsg = null;
    this.apiSvc.getAuthToken(this.form).subscribe(
      (auth: AuthModel) => {
        this.success = true;
        this.loading = false;
        localStorage.setItem('token', auth.token);
        localStorage.setItem('user', JSON.stringify(auth.user));
        this.globals.userLoggedIn = auth.user;
        this.router.navigate(['../tooeats']);
      },
      (err: HttpErrorResponse) => {
        this.loading = false;
        switch (err.status) {
          case 401:
            this.errorMsg = "Usuário ou senha inválida!";
            break;
          default:
            this.errorMsg = "Falha ao tentar realizar o login, tente mais tarde!";
            break;
        }
      })
  }

  isValidUsername() {
    if (this.form.username.includes('@')) {
      let reg: RegExp = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      return reg.test(this.form.username)
    }
    else return this.form.username.length > 0
  }

}
