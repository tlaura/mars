import {Component, EventEmitter, HostListener, OnInit, Output} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css']
})
export class AddressComponent implements OnInit {
  ZIPCODE_REGEX = '^[1-9][0-9]{3}$';

  @Output()
  zipcodeEmitter: EventEmitter<number>;
  @Output()
  cityEmitter: EventEmitter<string>;
  @Output()
  addressEmitter: EventEmitter<string>;


  zipcode: FormControl;
  city: FormControl;
  address: FormControl;


  constructor() {
    this.zipcode = new FormControl(null, [Validators.pattern(this.ZIPCODE_REGEX)]);
    this.city = new FormControl('');
    this.address = new FormControl('');
    this.zipcodeEmitter = new EventEmitter<number>();
    this.cityEmitter = new EventEmitter<string>();
    this.addressEmitter = new EventEmitter<string>();
  }

  @HostListener('animationstart')
  onAutoFillStart() {
    this.emitZipcode();
    this.emitCity();
    this.emitAddress();
  }

  ngOnInit(): void {
  }

  emitZipcode() {
    console.log("zipcode emitted");
    this.zipcodeEmitter.emit(this.zipcode.value)
  }

  emitCity() {
    console.log("city emitted");
    this.cityEmitter.emit(this.city.value);
  }

  emitAddress() {
    console.log("address emitted");
    this.addressEmitter.emit(this.address.value);
  }

}
