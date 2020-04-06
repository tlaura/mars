import {Component, Input, OnInit} from '@angular/core';
import {FormGroup, Validators} from "@angular/forms";
import {validatorBounds} from "../../../../environments/validatorBounds";
import {InstitutionListModel} from "../../../account-institution/institution/models/institutionList.model";

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css']
})
export class AddressComponent implements OnInit {
  private ZIPCODE_MIN = validatorBounds.zipcodeMin;
  private ZIPCODE_MAX = validatorBounds.zipcodeMax;

  @Input() addressFormGroup: FormGroup = new FormGroup({});

  @Input()
  selectedInstitution: boolean;

  @Input()
  institutionModel: InstitutionListModel;

  constructor() {
  }

  ngOnInit(): void {
    this.addressFormGroup.get('zipcode').setValidators([Validators.min(this.ZIPCODE_MIN), Validators.max(this.ZIPCODE_MAX)]);
    this.addressFormGroup.get('address').setValidators(Validators.required);
    this.addressFormGroup.get('city').setValidators(Validators.required);
  }

}
