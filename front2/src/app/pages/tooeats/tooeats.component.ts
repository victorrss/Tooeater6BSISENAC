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
  tooeats: TooeatModel[] = [];
  errorMsg: string = null;
  apiSubscription: any;
  tooeatSelected:number;
  constructor(private apiSvc: ApiService, protected global: Globals, private modalService: NgbModal) { }

  ngOnInit() {
    this.apiSubscription = this.apiSvc.getTooeatFeed().subscribe((result: TooeatModel[]) => {
      this.tooeats = result;
    }, (err: HttpErrorResponse) => {
      this.errorMsg = "Não foi possível consultar os últimos tooeats";// if (err.status == 400)
    })
  }

  ngOnDestroy(): void {
    this.apiSubscription.unsubscribe();
  }

  openModalComments(content, tooeatId) {
    this.tooeatSelected = tooeatId;
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title', size: 'lg' })
  }
}
