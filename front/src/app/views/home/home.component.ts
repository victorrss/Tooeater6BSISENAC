import { Component, OnInit } from '@angular/core';
import { Tooeat } from '../../models/tooeat';
import { TooeatService } from '../../services/tooeat.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  tooeats: Array<Tooeat>;
  tooeat: Tooeat;
  tooeatSel = new Tooeat(); // Por enquanto.

  constructor(private tooeatService: TooeatService) {
    console.log('Entrei');
  }

  ngOnInit() {
  }

  OnSelect(tooeat: Tooeat): void {
    this.tooeatSel = tooeat;
  }

  newTooeat(): void {
    this.tooeatSel = new Tooeat();
  }

  addTooeat(): void {
    console.log(JSON.stringify(this.tooeatSel));
    this.tooeatService.addTooeat(this.tooeatSel).subscribe(
      (result) => {
        console.log(result);
        alert('Tooeat postado!');
      },
      (err) => {
        console.log(err);
      }
    );
  }
  listTooeats(): void {
    this.tooeatService.listTooeats().subscribe(
      tooeatFromBackend => {
        this.tooeats = tooeatFromBackend;
      }
    );
  }

  remove(id: number): void {
    this.tooeatService.remove(id).subscribe(() => {
      alert('Tooeat deletado');
    });
  }

  update(id: number): void {
    this.tooeatService.update(this.tooeatSel).subscribe(() => {
      alert('Tooeat atualizado');
    });
  }

}
