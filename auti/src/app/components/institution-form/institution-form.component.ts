import {Component, OnInit} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {Router} from "@angular/router";
import {InstitutionService} from "../../services/institution.service";
import {validationHandler} from "../../utils/validationHandler";

@Component({
  selector: 'app-institution-form',
  templateUrl: './institution-form.component.html',
  styleUrls: ['./institution-form.component.css']
})
export class InstitutionFormComponent implements OnInit {

  institutionForm = this.formBuilder.group({
    "name": ['',],
    "zipCode": [''],
    "city": [''],
    "address": [''],
    "email": [''],
    "description": [''],
    "creatorId": [0] //TODO Should be current users Id
  });

  constructor(private formBuilder: FormBuilder,
              private institutionService: InstitutionService,
              private router: Router) {
  }

  ngOnInit() {
  }

  submit() {
    this.institutionService.saveInstitution(this.institutionForm.value)
      .subscribe(
        () => this.router.navigate(["institute-list"]),
        error => validationHandler(error, this.institutionForm),
      );

  }
}
