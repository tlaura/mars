import {Component, Input, OnInit} from '@angular/core';
import {FormGroup, Validators} from "@angular/forms";
import {validatorBounds} from "../../../../environments/validatorBounds";

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.css']
})
export class ContactsComponent implements OnInit {
  private PHONE_REGEX = validatorBounds.phoneRegex;
  private WEBSITE_REGEX = validatorBounds.websiteRegex;
  private NAME_REGEX = validatorBounds.nameRegex;


  @Input() contactsFormGroup: FormGroup = new FormGroup({});

  constructor() {
  }

  ngOnInit(): void {
    this.contactsFormGroup.get('phone').setValidators([Validators.required, Validators.pattern(this.PHONE_REGEX)]);
    this.contactsFormGroup.get('website').setValidators(Validators.pattern(this.WEBSITE_REGEX));
    this.contactsFormGroup.get('name').setValidators([Validators.required, Validators.pattern(this.NAME_REGEX)]);
  }
}
