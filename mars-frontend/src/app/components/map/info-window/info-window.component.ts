import {AfterViewInit, Component, Input} from '@angular/core';
import {AgmSnazzyInfoWindow} from "@agm/snazzy-info-window";

@Component({
  selector: 'app-info-window',
  styleUrls: ['./info-window.component.css'],
  template:
    "<div #outerWrapper><div #viewContainer></div></div><ng-content></ng-content>"
})
export class InfoWindowComponent extends AgmSnazzyInfoWindow implements AfterViewInit {

  @Input() edgeOffset: {
    top: number,
    right: number,
    bottom: number,
    left: number
  };
  @Input() closeWhenOthersOpen;
  @Input() isOpen;
  @Input() placement;
  @Input() backgroundColor;
  @Input() borderRadius;
  @Input() maxHeight;
  @Input() maxWidth;


  ngAfterViewInit() {
    super.ngAfterViewInit();
    this._snazzyInfoWindowInitialized.then(() => {
      this._nativeSnazzyInfoWindow._opts.edgeOffset = this.edgeOffset;
      this._nativeSnazzyInfoWindow._opts.closeWhenOthersOpen = this.closeWhenOthersOpen;
      this._nativeSnazzyInfoWindow._opts.isOpen = this.isOpen;
      this._nativeSnazzyInfoWindow._opts.placement = this.placement;
      this._nativeSnazzyInfoWindow._opts.backgroundColor = this.backgroundColor;
      this._nativeSnazzyInfoWindow._opts.borderRadius = this.borderRadius;
      this._nativeSnazzyInfoWindow._opts.maxHeight = this.maxHeight;
      this._nativeSnazzyInfoWindow._opts.maxWidth = this.maxWidth;
    });
  }
}
