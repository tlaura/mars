import {Component, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {InstitutionService} from "../../../services/institution.service";
import {validationHandler} from "../../../utils/validationHandler";
import {Institution} from "../../../models/institution";

@Component({
  selector: 'app-institution-form',
  templateUrl: './institution-form.component.html',
  styleUrls: ['./institution-form.component.css']
})
export class InstitutionFormComponent implements OnInit {

  institutionForm: FormGroup;

  constructor(private institutionService: InstitutionService,
              private router: Router) {
    this.institutionForm = new FormGroup({
      'zipcode': new FormControl(null, Validators.required),
      'city': new FormControl(null, Validators.required),
      'address': new FormControl(null, Validators.required),

      'name': new FormControl('', Validators.required),
      'email': new FormControl('', Validators.required),
      'phone': new FormControl('', Validators.required),
      'website': new FormControl(),

      'description': new FormControl('',
        [Validators.required, Validators.minLength(30), Validators.maxLength(200)]),

      'openingHours': new FormArray([])
    })
  }

  ngOnInit() {
  }


  //      const formData: ProviderAccountRegisterModel = this.registerForm.value;
  submit() {
    const formData: Institution = this.institutionForm.value;
    this.institutionService.saveInstitution(formData)
      .subscribe(
        () => this.router.navigate(["institution-list"]),
        error => validationHandler(error, this.institutionForm),
      );

  }
}
