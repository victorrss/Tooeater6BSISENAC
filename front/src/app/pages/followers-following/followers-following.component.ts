import { Component, OnInit, OnDestroy } from "@angular/core";
import { Globals } from "src/app/services/globals";
import { UserModel } from "src/app/model/user.model";
import { ApiService } from "src/app/services/api.service";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import {
  ActivatedRoute,
  Router,
  NavigationStart,
  NavigationEnd,
  NavigationError
} from "@angular/router";
import { FollowerModel } from "../../model/follower.model";
import { HttpErrorResponse } from "@angular/common/http";

@Component({
  selector: "app-followers-following",
  templateUrl: "./followers-following.component.html",
  styleUrls: ["./followers-following.component.scss"]
})
export class FollowersFollowingComponent implements OnInit, OnDestroy {
  apiSubscription: any;
  today = new Date();
  mode: string;
  results: FollowerModel[] = [];
  followerSubscription: any;
  constructor(
    private globals: Globals,
    private apiSvc: ApiService,
    private modalService: NgbModal,
    private route: ActivatedRoute,
    private router: Router
  ) {
    router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        // Show loading indicator
        this.mode = <string>this.route.snapshot.params["followersOrfollowing"];
        if (this.mode === "followers") {
          this.apiSubscription = this.apiSvc.getFollowers().subscribe(
            (res: FollowerModel[]) => {
              console.log("GET FOLLOWERS", res);

              this.results = res;
            },
            (err: HttpErrorResponse) => {
              if (err.status === 406) {
                this.globals.showToast("Oh não!", err.error.message, "error");
              } else {
                this.globals.showToast(
                  "Oh não!",
                  this.globals.msgErrApi,
                  "error"
                );
              }
            }
          );
        } else if (this.mode === "following") {
          this.apiSubscription = this.apiSvc.getFollowing().subscribe(
            (res: FollowerModel[]) => {
              console.log("getFollowing", res);
              this.results = res;
            },
            (err: HttpErrorResponse) => {
              if (err.status === 406) {
                this.globals.showToast("Oh não!", err.error.message, "error");
              } else {
                this.globals.showToast(
                  "Oh não!",
                  this.globals.msgErrApi,
                  "error"
                );
              }
            }
          );
        } else {
          this.globals.goToTooeats();
        }
      }

      if (event instanceof NavigationEnd) {
        // Hide loading indicator
      }

      if (event instanceof NavigationError) {
        // Hide loading indicator

        // Present error to user
        console.log(event.error);
      }
    });
  }

  ngOnInit() {}

  ngOnDestroy(): void {
    if (this.apiSubscription) {
      this.apiSubscription.unsubscribe();
    }
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
}
