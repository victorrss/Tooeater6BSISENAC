import { Component, OnInit, OnDestroy } from '@angular/core';
import { Globals } from 'src/app/services/globals';
import { UserModel } from 'src/app/model/user.model';
import { ApiService } from 'src/app/services/api.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute, Router, NavigationStart, NavigationEnd, NavigationError } from '@angular/router';
import { FollowerModel } from '../../model/follower.model';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-followers-following',
  templateUrl: './followers-following.component.html',
  styleUrls: ['./followers-following.component.scss']
})
export class FollowersFollowingComponent implements OnInit, OnDestroy {
  apiSubscription: any;
  today = new Date();
  mode: string;
  results: FollowerModel[] = [];
  constructor(private globals: Globals, private apiSvc: ApiService,
    private modalService: NgbModal, private route: ActivatedRoute, private router: Router) {
    router.events.subscribe((event) => {

      if (event instanceof NavigationStart) {
        // Show loading indicator
        this.mode = <string>this.route.snapshot.params['followersOrfollowing'];
        if (this.mode === 'followers') {
          this.apiSubscription = this.apiSvc.getFollowers().subscribe(
            (res: FollowerModel[]) => {
              this.results = res;
            },
            (err: HttpErrorResponse) => {
              if (err.status === 406) {
                this.globals.showToast('Oh não!', err.error.message, 'error');
              } else {
                this.globals.showToast('Oh não!', this.globals.msgErrApi, 'error');
              }
            });
        } else if (this.mode === 'following') {
          this.apiSubscription = this.apiSvc.getFollowing().subscribe(
            (res: FollowerModel[]) => {
              this.results = res;
            },
            (err: HttpErrorResponse) => {
              if (err.status === 406) {
                this.globals.showToast('Oh não!', err.error.message, 'error');
              } else {
                this.globals.showToast('Oh não!', this.globals.msgErrApi, 'error');
              }
            });
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

  ngOnInit() {


  }

  ngOnDestroy(): void {
    if (this.apiSubscription) {
      this.apiSubscription.unsubscribe();
    }
  }

  goToUserProfile(user: UserModel) {
    this.globals.goToUser(user.nickname);
  }
}
