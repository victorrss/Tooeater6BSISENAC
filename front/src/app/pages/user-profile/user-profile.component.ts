import { Component, OnInit, ViewChild } from "@angular/core";
import { Globals } from "src/app/services/globals";
import { UserModel } from "src/app/model/user.model";
import { ApiService } from "src/app/services/api.service";
import { HttpErrorResponse } from "@angular/common/http";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { ActivatedRoute } from "@angular/router";
import { SearchModel } from "../../model/search.model";
import { TooeatModel } from "../../model/tooeat.model";

@Component({
  selector: "app-user-profile",
  templateUrl: "./user-profile.component.html",
  styleUrls: ["./user-profile.component.scss"]
})
export class UserProfileComponent implements OnInit {
  user = new UserModel();
  tooeats: TooeatModel[] = [];
  apiSubscription: any;
  today = new Date();
  nickname: string;
  followerSubscription: any;
  constructor(
    private globals: Globals,
    private apiSvc: ApiService,
    private modalService: NgbModal,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.nickname = <string>this.route.snapshot.params["nickname"];
    this.getUser();
  }

  // tslint:disable-next-line: use-life-cycle-interface
  ngOnDestroy(): void {
    if (this.apiSubscription) {
      this.apiSubscription.unsubscribe();
    }
  }

  getUser() {
    this.apiSubscription = this.apiSvc.getByNickname(this.nickname).subscribe(
      (result: SearchModel) => {
        this.user = result.user;
        this.tooeats = result.tooeats;
        if (!this.user) {
          this.globals.showToast("Oh não", "Usuário não encontrado!", "error");
          this.globals.goToTooeats();
        }
      },
      err => {
        this.globals.showToast(
          "Oh não!",
          "Falha ao carregar o usuário",
          "error"
        );
        this.globals.goToTooeats();
      }
    );
  }

  followDisfollow(user: UserModel) {
    this.followerSubscription = this.apiSvc
      .postFollowerInvite(user.nickname)
      .subscribe(
        res => {
          console.log(res);

          switch (res.status) {
            /*
          @ApiResponse(code = 201, message = "Follow!"),
          @ApiResponse(code = 204, message = "Disfollow!"),
          */
            case 201:
              user.isFollower = true;
              this.globals.showToast(
                "Aí sim!",
                "Agora você um seguidor",
                "success"
              );
              break;
            case 204:
              user.isFollower = false;
              this.globals.showToast(
                "Aí sim!",
                "Você não é mais um seguidor de @" + user.nickname,
                "success"
              );
              break;
            default:
              user.isFollower = false;
              break;
          }
        },
        (err: HttpErrorResponse) => {
          if (err.status == 406)
            this.globals.showToast("Oh não!", err.error.message, "error");
          else
            this.globals.showToast("Oh não!", this.globals.msgErrApi, "error");
        }
      );
  }
}
