import {Component, Input, OnInit} from '@angular/core';
import {FormGroup, Validators} from "@angular/forms";
import {validatorBounds} from "../../../../environments/validatorBounds";
import {InstitutionListModel} from "../../../account-institution/institution/models/institutionList.model";

@Component({
  selector: 'app-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.css']
})
export class EmailComponent implements OnInit {
  private EMAIL_REGEX = validatorBounds.emailRegex;

  @Input()
  emailFormGroup: FormGroup = new FormGroup({});

  @Input()
  selectedInstitution: boolean;

  @Input()
  institutionModel: InstitutionListModel;

  constructor() {
  }

  ngOnInit(): void {
    this.emailFormGroup.get('email').setValidators([Validators.required, Validators.pattern(this.EMAIL_REGEX)]);
  }

}
