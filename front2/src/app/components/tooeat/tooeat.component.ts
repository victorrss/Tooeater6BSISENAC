import { Component, Input, Output, EventEmitter } from '@angular/core';
import { TooeatModel } from 'src/app/model/tooeat.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Globals } from 'src/app/services/globals';

import { ApiService } from 'src/app/services/api.service';

@Component({
    selector: 'app-tooeat',
    templateUrl: './tooeat.component.html',
})
export class TooeatComponent {
    formUpdate = { text: '' }
    apiSubscription: any;
    @Input('tooeat') t: TooeatModel;
    @Output() onDelete = new EventEmitter<boolean>();
    @Output() onUpdate = new EventEmitter<boolean>();
    mode: string = 'create' // create or update
    tooeatUpdateStage: TooeatModel;
    constructor(private modalService: NgbModal, private globals: Globals, private apiSvc: ApiService) { }

    openModalComments(content) {
        this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title', size: 'lg' })
    }

    delete() {
        this.apiSubscription = this.apiSvc.deleteTooeat(this.t.id).subscribe(
            () => this.onDelete.emit(true),
            () => this.onDelete.emit(false)
        )
    }

    onSubmitUpdate() {
        let tooeat = new TooeatModel();
        tooeat.text = this.formUpdate.text;
        this.apiSubscription = this.apiSvc.updateTooeat(this.t.id, tooeat).subscribe(
            () => {
                this.onUpdate.emit(true)
                this.t.text = this.formUpdate.text;
            },
            () => this.onUpdate.emit(false)
        )
    }

    changeMode() {
        if (this.mode == 'create') {
            this.mode = 'update'
            this.formUpdate.text = this.t.text;
        }
        else
            this.mode = 'create'
    }
}
