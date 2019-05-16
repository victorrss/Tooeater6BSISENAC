import { Routes } from '@angular/router';


import { UserProfileComponent } from '../../pages/user-profile/user-profile.component';
import { TooeatsComponent } from 'src/app/pages/tooeats/tooeats.component';

export const AppLayoutRoutes: Routes = [
    { path: 'tooeats', component: TooeatsComponent },
    { path: 'user-profile', component: UserProfileComponent },
];
