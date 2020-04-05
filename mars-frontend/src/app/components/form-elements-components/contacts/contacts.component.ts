import {Component, Input, OnInit} from '@angular/core';
import {FormGroup, Validators} from "@angular/forms";
import {validatorBounds} from "../../../../environments/validatorBounds";
import {InstitutionListModel} from "../../../account-institution/institution/models/institutionList.model";

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.css']
})
export class ContactsComponent implements OnInit {
  private PHONE_REGEX = validatorBounds.phoneRegex;
  private WEBSITE_REGEX = validatorBounds.websiteRegex;

  @Input() contactsFormGroup: FormGroup = new FormGroup({});

  @Input()
  selectedInstitution: boolean;

  @Input()
  institutionModel: InstitutionListModel;

  constructor() {
  }

  ngOnInit(): void {
    this.contactsFormGroup.get('phone').setValidators([Validators.required, Validators.pattern(this.PHONE_REGEX)]);
    this.contactsFormGroup.get('website').setValidators(Validators.pattern(this.WEBSITE_REGEX));
    this.contactsFormGroup.get('name').setValidators([Validators.required]);
  }
}
