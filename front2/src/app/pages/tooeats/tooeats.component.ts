import { Component, OnInit, OnDestroy } from '@angular/core';
import { Globals } from 'src/app/services/globals';
import { ApiService } from 'src/app/services/api.service';
import { TooeatModel } from 'src/app/model/tooeat.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-tooeats',
  templateUrl: './tooeats.component.html',
  styleUrls: ['./tooeats.component.scss']
})
export class TooeatsComponent implements OnInit, OnDestroy {
  form = { text: '' }
  tooeats: TooeatModel[] = [];
  apiSubscription: any;
  tooeatSelected: number;

  constructor(private apiSvc: ApiService, private globals: Globals, private modalService: NgbModal) { }

  ngOnInit() {
    this.getTooeats();
  }

  ngOnDestroy(): void {
    this.apiSubscription.unsubscribe();
  }

  onSubmit() {
    let t = new TooeatModel();
    t.text = this.form.text;
    this.sendTooeat(t);
  }

  sendTooeat(t: TooeatModel) {
    this.apiSubscription = this.apiSvc.postTooeat(t).subscribe(
      () => {
        this.form.text = '';
        this.getTooeats();
      },
      (err) => this.globals.showToast('Erro!', err.error.message, 'error')
    )
  }

  deleteTooeat(ev: Event, t: TooeatModel) {
    if (ev) {
      this.tooeats = this.globals.arrayRemove(this.tooeats, t)
      //this.globals.showToast('Sucesso!', 'Tooeat excluído com sucesso!', 'success')
    }
    else this.globals.showToast('Erro!', "Não foi possível excluir seu tooeat!", 'error')
  }

  updateTooeat(ev: any) {
    if (!ev.success) 
      this.globals.showToast('Erro!', ev.message, 'error')
  }

  getTooeats() {
    this.apiSubscription = this.apiSvc.getTooeatFeed().subscribe(
      (result: TooeatModel[]) => this.tooeats = result,
      () => this.globals.showToast('Erro!', "Não foi possível consultar os últimos tooeats", 'error'))
  }
}
