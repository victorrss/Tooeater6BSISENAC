import { Component, OnInit, ViewChild, Input } from "@angular/core";
import { ImageCropperComponent, ImageCroppedEvent } from "ngx-image-cropper";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: "app-image-crop-picker",
  templateUrl: "./image-crop-picker.component.html"
})
export class ImageCropPickerComponent implements OnInit {
  imageChangedEvent: any = "";
  croppedImage: any = "";
  showCropper = false;

  @Input() image;
  @ViewChild(ImageCropperComponent) imageCropper: ImageCropperComponent;

  constructor(public activeModal: NgbActiveModal) {}
  ngOnInit() {}
  selectImage() {
    this.activeModal.close(this.croppedImage);
    // this.activeModal.dismiss({ image: this.croppedImage });
  }

  fileChangeEvent(event: any): void {
    this.imageChangedEvent = event;
  }
  imageCropped(event: ImageCroppedEvent) {
    this.croppedImage = event.base64;
    // console.log(event);
  }
  imageLoaded() {
    this.showCropper = true;
    // console.log("Image loaded");
  }
  cropperReady() {
    // console.log("Cropper ready");
  }
  loadImageFailed() {
    // console.log("Load failed");
  }
  rotateLeft() {
    this.imageCropper.rotateLeft();
  }
  rotateRight() {
    this.imageCropper.rotateRight();
  }
  flipHorizontal() {
    this.imageCropper.flipHorizontal();
  }
  flipVertical() {
    this.imageCropper.flipVertical();
  }
}
