import { Component } from '@angular/core';
import { Globals } from './services/globals';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'tooeater';

  constructor(protected globals: Globals) { }

}
