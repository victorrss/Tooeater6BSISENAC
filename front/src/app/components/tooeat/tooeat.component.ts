import {
  Component,
  Input,
  Output,
  EventEmitter,
  ViewChild,
  ElementRef,
  Renderer2
} from '@angular/core';
import { TooeatModel } from 'src/app/model/tooeat.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Globals } from 'src/app/services/globals';

import { ApiService } from 'src/app/services/api.service';
import { HttpErrorResponse } from '@angular/common/http';
import { CommentModel } from 'src/app/model/comment.model';

@Component({
  selector: 'app-tooeat',
  templateUrl: './tooeat.component.html'
})
export class TooeatComponent {
  formUpdateTooeat = { text: '' };
  formSubmitComment = { text: '' };
  apiSubscription: any;
  @Input('tooeat') t: TooeatModel;
  @Output() onDelete = new EventEmitter<boolean>();
  @Output() onUpdate = new EventEmitter<any>();

  mode: string = 'create'; // create or update
  tooeatUpdateStage: TooeatModel;
  constructor(
    private modalService: NgbModal,
    protected globals: Globals,
    private apiSvc: ApiService,
    private _renderer: Renderer2
  ) {}

  changeModeTooeat() {
    if (this.mode == 'create') {
      this.mode = 'update';
      this.formUpdateTooeat.text = this.t.text;
    } else this.mode = 'create';
  }

  onSubmitUpdateTooeat() {
    const tooeat = new TooeatModel();
    tooeat.id = this.t.id;
    tooeat.text = this.formUpdateTooeat.text;
    this.apiSubscription = this.apiSvc.updateTooeat(tooeat).subscribe(
      () => {
        this.onUpdate.emit({ success: true });
        this.t.text = this.formUpdateTooeat.text;
        this.t.updateAt = new Date();
        this.changeModeTooeat();
      },
      (err: HttpErrorResponse) => {
        const msg =
          err.status == 406 ? err.error.message : this.globals.msgErrApi;
        this.onUpdate.emit({ success: false, message: msg });
      }
    );
  }

  deleteTooeat() {
    this.apiSubscription = this.apiSvc
      .deleteTooeat(this.t.id)
      .subscribe(
        () => this.onDelete.emit(true),
        () => this.onDelete.emit(false)
      );
  }

  openModalComments(content) {
    this.apiSubscription = this.apiSvc.getComments(this.t.id).subscribe(
      (comments: CommentModel[]) => {
        this.t.commentsObj = comments;
        this.modalService.open(content, {
          ariaLabelledBy: 'modal-basic-title',
          size: 'lg'
        });
      },
      (err: HttpErrorResponse) => {
        this.globals.showToast('Oh não!', this.globals.msgErrApi, 'error');
      }
    );
  }

  onSubmitComment() {
    const comment = new CommentModel();
    comment.text = this.formSubmitComment.text;
    this.apiSubscription = this.apiSvc
      .postComment(this.t.id, comment)
      .subscribe(
        (comment: CommentModel) => {
          this.t.commentsObj.push(comment);
          this.formSubmitComment.text = '';
        },
        (err: HttpErrorResponse) => {
          const msg =
            err.status == 406 ? err.error.message : this.globals.msgErrApi;
          this.globals.showToast('Oh não!', msg, 'error');
        }
      );
  }

  deleteComment(comment: CommentModel) {
    this.apiSubscription = this.apiSvc
      .deleteComment(comment.id)
      .subscribe(
        () =>
          (this.t.commentsObj = this.globals.arrayRemove(
            this.t.commentsObj,
            comment
          )),
        () => this.globals.showToast('Oh não!', this.globals.msgErrApi, 'error')
      );
  }

  likeDislike(t: TooeatModel) {
    this.apiSubscription = this.apiSvc.getLikeDislike(t.id).subscribe(
      res => {
        if (res.status == 201) this.t.likes += 1;
        else if (res.status == 204) this.t.likes -= 1;
      },
      () => this.globals.showToast('Oh não!', this.globals.msgErrApi, 'error')
    );
  }

}
