import { Component, OnInit, OnDestroy } from '@angular/core';
import { ApiService } from 'src/app/services/api.service';
import { TooeatModel } from 'src/app/model/tooeat.model';
import { HttpErrorResponse } from '@angular/common/http';
import { Globals } from 'src/app/services/globals';

import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-tooeats',
  templateUrl: './tooeats.component.html',
  styleUrls: ['./tooeats.component.scss']
})
export class TooeatsComponent implements OnInit, OnDestroy {
  form = {
    text: ''
  }
  tooeats: TooeatModel[] = [];
  errorMsg: string = null;
  apiSubscription: any;
  tooeatSelected: number;
  constructor(private apiSvc: ApiService, protected globals: Globals, private modalService: NgbModal) { }

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

  sendTooeat(t) {
    this.apiSubscription = this.apiSvc.postTooeat(t).subscribe((result) => {
      this.form.text = '';
      this.getTooeats();
    }, (err) => {
      this.errorMsg = "Não foi possível enviar seu tooeat!";
    })
  }

  deleteTooeat(t:TooeatModel){
    this.apiSubscription = this.apiSvc.deleteTooeat(t.id).subscribe((result) => {
      this.tooeats = this.globals.arrayRemove(this.tooeats,t);
    }, (err) => {
      this.errorMsg = "Não foi possível excluir seu tooeat!";
    })
  }
  
  getTooeats() {
    this.apiSubscription = this.apiSvc.getTooeatFeed().subscribe((result: TooeatModel[]) => {
      this.tooeats = result;
    }, (err: HttpErrorResponse) => {
      this.errorMsg = "Não foi possível consultar os últimos tooeats";// if (err.status == 400)
    })
  }

  openModalComments(content, tooeatId) {
    this.tooeatSelected = tooeatId;
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title', size: 'lg' })
  }
}
