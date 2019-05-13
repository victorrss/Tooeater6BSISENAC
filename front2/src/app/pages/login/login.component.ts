import { Component, OnInit, OnDestroy } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

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
  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit() {
    if (this.authService.isAuthenticated())
      this.router.navigate(['../tooeats']);
  }
  ngOnDestroy() { }

  onSubmit() {
    this.loading = true;
    this.errorMsg = null;
    this.authService.getToken(this.form).subscribe(
      (success: any) => {
        this.success = true;
        this.loading = false;
        localStorage.setItem('token', success.token);
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
