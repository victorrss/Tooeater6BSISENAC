import { Routes } from '@angular/router';


import { TooeatsComponent } from 'src/app/pages/tooeats/tooeats.component';
import { MyProfileComponent } from '../../pages/my-profile/my-profile.component';
import { SearchComponent } from '../../pages/search/search.component';
import { UserProfileComponent } from 'src/app/pages/user-profile/user-profile.component';
import { FollowersFollowingComponent } from '../../pages/followers-following/followers-following.component';

export const AppLayoutRoutes: Routes = [
  { path: 'tooeats', component: TooeatsComponent },
  { path: 'my-profile', component: MyProfileComponent },
  { path: 'search/:term', component: SearchComponent },
  { path: 'profile/:nickname', component: UserProfileComponent },
  { path: 'my-profile/:followersOrfollowing', component: FollowersFollowingComponent }
];
