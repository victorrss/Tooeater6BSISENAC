import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { UserComponent } from './views/user/user.component';
import { SignUpComponent } from './views/user/sign-up/sign-up.component';
import { SignInComponent } from './views/user/sign-in/sign-in.component';
import { TooeatComponent } from './views/tooeat/tooeat.component';


export const appRoutes: Routes = [
    { path: 'home', component: HomeComponent },
    {
        path: 'signup', component: UserComponent,
        children: [{ path: '', component: SignUpComponent }]
    },
    {
        path: 'signin', component: UserComponent,
        children: [{ path: '', component: SignInComponent }]
    },
    {
        path: 'tooeat', component: TooeatComponent,
        children: [{ path: '', component: TooeatComponent }]
    },
    { path: '', redirectTo: '/signin', pathMatch: 'full' }

];