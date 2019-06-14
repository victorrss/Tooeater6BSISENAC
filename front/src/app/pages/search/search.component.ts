import { Component, OnInit } from "@angular/core";
import { Globals } from "src/app/services/globals";
import { UserModel } from "src/app/model/user.model";
import { ApiService } from "src/app/services/api.service";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { ActivatedRoute } from "@angular/router";
import { SearchModel } from "../../model/search.model";
import { HttpErrorResponse } from "@angular/common/http";

@Component({
  selector: "app-search",
  templateUrl: "./search.component.html",
  styleUrls: ["./search.component.scss"]
})
export class SearchComponent implements OnInit {
  user = new UserModel();
  apiSubscription: any;
  followerSubscription: any;
  today = new Date();
  term: string;
  results: SearchModel[] = [];
  constructor(
    private globals: Globals,
    private apiSvc: ApiService,
    private modalService: NgbModal,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.term = <string>this.route.snapshot.params["term"];
    this.apiSubscription = this.apiSvc.getSearchUser(this.term).subscribe(
      (res: SearchModel[]) => {
        this.results = res;
        this.verifyFollower();
      },
      (err: HttpErrorResponse) => {
        if (err.status == 406)
          this.globals.showToast("Oh não!", err.error.message, "error");
        else this.globals.showToast("Oh não!", this.globals.msgErrApi, "error");
      }
    );
  }

  ngOnDestroy(): void {
    if (this.apiSubscription) this.apiSubscription.unsubscribe();
    if (this.followerSubscription) this.followerSubscription.unsubscribe();
  }

  goToUserProfile(user: UserModel) {
    this.globals.goToUser(user.nickname);
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
  verifyFollower() {
    for (let i = 0; i < this.results.length; i++) {
      this.apiSvc.getIsFollower(this.results[i].user.nickname).subscribe(
        res => {
          /*
            @ApiResponse(code = 200, message = "ESTÁ SEGUINDO"),
            @ApiResponse(code = 206, message = "NÃO ESTÁ SEGINDO"),
            @ApiResponse(code = 400, message = "Falha geral, try-catch"),
            @ApiResponse(code = 403, message = "enabled == false, não foi implementado")
            */
          switch (res.status) {
            case 200:
              this.results[i].user.isFollower = true;
              break;
            case 206:
              this.results[i].user.isFollower = false;
              break;
            default:
              this.results[i].user.isFollower = false;
              break;
          }
          console.log(res, this.results[i].user);
        },
        (err: HttpErrorResponse) => {
          console.log(err);
          // if (err.status == 406) this.globals.showToast('Oh não!', err.error.message, 'error')
          // else this.globals.showToast('Oh não!', this.globals.msgErrApi, 'error')
        }
      );
    }
  }
}
