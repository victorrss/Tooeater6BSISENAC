import { Component, OnInit } from '@angular/core';
import { Globals } from 'src/app/services/globals';
import { UserModel } from 'src/app/model/user.model';
import * as moment from 'moment';
import { ApiService } from 'src/app/services/api.service';
import { HttpErrorResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ImageCropPickerComponent } from 'src/app/components/image-crop-picker/image-crop-picker.component';

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.scss']
})
export class MyProfileComponent implements OnInit {
  user = new UserModel();
  apiSubscription: any;
  today = new Date();

  constructor(private globals: Globals, private apiSvc: ApiService, private modalService: NgbModal) { }

  ngOnInit() {
    this.getUser();
  }

  ngOnDestroy(): void {
    if (this.apiSubscription)
      this.apiSubscription.unsubscribe();
  }

  onSubmit() {
    let userUpdate = this.user;
    delete (userUpdate.tooeats)
    delete (userUpdate.following)
    delete (userUpdate.followers)
    delete (userUpdate.email)
    delete (userUpdate.createdAt)
    delete (userUpdate.updateAt)

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
      this.globals.userLoggedIn = this.user = result;
    }, (err) => {
      this.globals.showToast('Oh não!', 'Falha ao consultar os dados de seu usuário', 'error')
    });
  }

  openModalImageCropPicker() {
    const modalRef = this.modalService.open(ImageCropPickerComponent, { size: 'lg' });
    modalRef.componentInstance.image = this.user.photo ? this.user.photo : null;
    modalRef.result.then((result) => {
      console.log(result)
    }).catch();
  }
}
