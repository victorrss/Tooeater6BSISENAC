import { Routes } from '@angular/router';


import { TooeatsComponent } from 'src/app/pages/tooeats/tooeats.component';
import { MyProfileComponent } from '../../pages/my-profile/my-profile.component';

export const AppLayoutRoutes: Routes = [
    { path: 'tooeats', component: TooeatsComponent },
    { path: 'my-profile', component: MyProfileComponent },
];
