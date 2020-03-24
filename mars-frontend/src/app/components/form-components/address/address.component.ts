import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css']
})
export class AddressComponent implements OnInit {
  ZIPCODE_REGEX = '^[1-9][0-9]{3}$';

  @Input() addressFormGroup: FormGroup = new FormGroup({});

  zipcode: FormControl;
  city: FormControl;
  address: FormControl;


  constructor() {
    this.zipcode = new FormControl(this.addressFormGroup.get('zipcode'));
    this.city = new FormControl(this.addressFormGroup.get('city'));
    this.address = new FormControl(this.addressFormGroup.get('address'));
  }


  ngOnInit(): void {
  }

}
