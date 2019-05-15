import { Component, OnInit, ViewChild } from '@angular/core';
import { UserModel } from 'src/app/model/user.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  user: UserModel = new UserModel();

  constructor(private modalService: NgbModal) { }

  ngOnInit() { }

  openModalPolicy(content) {
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title', size: 'lg' })
  }


}