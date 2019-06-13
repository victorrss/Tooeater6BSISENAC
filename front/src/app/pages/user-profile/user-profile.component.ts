import { Component, OnInit, ViewChild } from '@angular/core';
import { Globals } from 'src/app/services/globals';
import { UserModel } from 'src/app/model/user.model';
import * as moment from 'moment';
import { ApiService } from 'src/app/services/api.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ImageCroppedEvent, ImageCropperComponent } from 'ngx-image-cropper';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ImageCropPickerComponent } from 'src/app/components/image-crop-picker/image-crop-picker.component';
import { ActivatedRoute } from '@angular/router';
import { SearchModel } from '../../model/search.model';
import { TooeatModel } from '../../model/tooeat.model';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  user = new UserModel();
  tooeats: TooeatModel[] = [];
  apiSubscription: any;
  today = new Date();
  nickname: string;

  constructor(private globals: Globals, private apiSvc: ApiService, private modalService: NgbModal, private route: ActivatedRoute) { }

  ngOnInit() {
    this.nickname = <string>this.route.snapshot.params['nickname'];
    this.getUser();
  }

  // tslint:disable-next-line: use-life-cycle-interface
  ngOnDestroy(): void {
    if (this.apiSubscription) {
      this.apiSubscription.unsubscribe();
    }
  }

  getUser() {
    this.apiSubscription = this.apiSvc.getByNickname(this.nickname).subscribe((result: SearchModel) => {
      this.user = result.user;
      this.tooeats = result.tooeats;
      if (!this.user) {
        this.globals.showToast('Oh não', 'Usuário não encontrado!', 'error');
        this.globals.goToTooeats();
      }
    }, (err) => {
      this.globals.showToast('Oh não!', 'Falha ao carregar o usuário', 'error');
      this.globals.goToTooeats();
    });
  }
}
