import { Routes } from '@angular/router';

import { IconsComponent } from '../../pages/icons/icons.component';
import { MapsComponent } from '../../pages/maps/maps.component';
import { UserProfileComponent } from '../../pages/user-profile/user-profile.component';
import { TablesComponent } from '../../pages/tables/tables.component';
import { TooeatsComponent } from 'src/app/pages/tooeats/tooeats.component';

export const AppLayoutRoutes: Routes = [
    { path: 'tooeats', component: TooeatsComponent },
    { path: 'user-profile', component: UserProfileComponent },
    { path: 'tables', component: TablesComponent },
    { path: 'icons', component: IconsComponent },
    { path: 'maps', component: MapsComponent },
];
