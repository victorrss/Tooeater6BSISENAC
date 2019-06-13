import { Component, OnInit } from '@angular/core';
import { Globals } from 'src/app/services/globals';
import { UserModel } from 'src/app/model/user.model';
import { ApiService } from 'src/app/services/api.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute } from '@angular/router';
import { SearchModel } from '../../model/search.model';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {
  user = new UserModel();
  apiSubscription: any;
  followerSubscription: any;
  today = new Date();
  term: string;
  results: SearchModel[] = [];
  constructor(private globals: Globals, private apiSvc: ApiService, private modalService: NgbModal, private route: ActivatedRoute) {

  }

  ngOnInit() {
    this.term = <string>this.route.snapshot.params['term'];
    this.apiSubscription = this.apiSvc.getSearchUser(this.term).subscribe(
      (res: SearchModel[]) => {
        this.results = res;
        this.verifyFollower();
      },
      (err: HttpErrorResponse) => {
        if (err.status == 406) this.globals.showToast('Oh não!', err.error.message, 'error')
        else this.globals.showToast('Oh não!', this.globals.msgErrApi, 'error')
      }
    )
  }

  ngOnDestroy(): void {
    if (this.apiSubscription) this.apiSubscription.unsubscribe();
    if (this.followerSubscription) this.followerSubscription.unsubscribe();
  }

  goToUserProfile(user: UserModel) {
    this.globals.goToUser(user.nickname);
  }

  followDisfollow(user: UserModel) {
    this.followerSubscription = this.apiSvc.postFollowerInvite(user.nickname).subscribe(
      (res) => {
        console.log(res);

        switch (res.status) {
          /*
          @ApiResponse(code = 201, message = "Follow!"),
          @ApiResponse(code = 204, message = "Disfollow!"),
          @ApiResponse(code = 206, message = "Aguardando aceitar o convite!"),
          */
          case 201:
            user.isFollower = 3;
            this.globals.showToast('Aí sim!', 'Convite enviado', 'success');
            break;
          case 204:
            user.isFollower = 1;
            this.globals.showToast('Aí sim!', 'Você não é mais um seguidor de @' + user.nickname, 'success');
            break;
          case 206:
            user.isFollower = 2;
            this.globals.showToast('Aí sim!', 'Aguardando aceite do invite', 'success');
            break;
          default:
            break;
        }
      },
      (err: HttpErrorResponse) => {
        if (err.status == 406) this.globals.showToast('Oh não!', err.error.message, 'error')
        else this.globals.showToast('Oh não!', this.globals.msgErrApi, 'error')
      }
    )
  }
  verifyFollower() {
    for (let i = 0; i < this.results.length; i++) {
      if (this.results[i].user.nickname) {
        let nickname: string = this.results[i].user.nickname;
        this.apiSvc.getIsFollower(nickname).subscribe(
          (res) => {
            /*
            @ApiResponse(code = 200, message = "ESTÁ SEGUINDO"),
            @ApiResponse(code = 204, message = "AGUARDANDO ACEITE DO INVITE"),
            @ApiResponse(code = 206, message = "NÃO ESTÁ SEGINDO"),
            @ApiResponse(code = 400, message = "Falha geral, try-catch"),
            @ApiResponse(code = 403, message = "enabled == false, não foi implementado")

            public isFollower: number = null;
              NULL - AGUARDANDO HTTP RESPONSE;
              1 - NAO SEGUE;
              2 - AGUARDANDO INVITE;
              3 - ESTÁ SEGUINDO.
            */
            switch (res.status) {
              case 200:
                this.results[i].user.isFollower = 3;
                break;
              case 204:
                this.results[i].user.isFollower = 2;
                break;
              case 206:
                this.results[i].user.isFollower = 1;
                break;
              default:
                break;
            }
            console.log(res, this.results[i].user)
          },
          (err: HttpErrorResponse) => {
            // if (err.status == 406) this.globals.showToast('Oh não!', err.error.message, 'error')
            // else this.globals.showToast('Oh não!', this.globals.msgErrApi, 'error')
          }
        )
      }
    }
  }

}
