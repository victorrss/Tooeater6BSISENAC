import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { UserModel } from 'src/app/model/user.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ApiService } from 'src/app/services/api.service';
import { Globals } from 'src/app/services/globals';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnDestroy {
  user: UserModel = new UserModel;
  apiSubscription: any;
  today = new Date();
  constructor(private modalService: NgbModal, private apiSvc: ApiService, public globals: Globals, private router: Router) { }

  ngOnDestroy(): void {
    if (this.apiSubscription)
      this.apiSubscription.unsubscribe();
  }

  openModalPolicy(content) {
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title', size: 'lg' })
  }

  onSubmit() {
    this.sendUser(this.user)
  }

  sendUser(user: UserModel) {
    delete (user.acceptPolicy);
    this.apiSubscription = this.apiSvc.postUser(user).subscribe(
      () => {
        this.router.navigate(['/login'])
        this.globals.showToast('Bem-vindo ao Tooeater!', "Cadastrado com sucesso. Efetue seu login!", 'success');
      },
      (err: HttpErrorResponse) => {
        if (err.status == 406) this.globals.showToast('Oh não!', err.error.message, 'error');
        else this.globals.showToast('Oh não!', "Erro ao tentar finalizar seu cadastro, tente mais tarde!", 'error');
      })
  }

}