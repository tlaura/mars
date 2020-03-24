import {Component, Input, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup} from "@angular/forms";

@Component({
    selector: 'app-institution',
    templateUrl: './institution.component.html',
    styleUrls: ['./institution.component.css']
})
export class InstitutionComponent implements OnInit {

    @Input()
    institutionForm: FormGroup;

    @Input()
    addressFormGroup: FormGroup;

    @Input()
    contactsFormGroup: FormGroup;

    constructor() {
        this.institutionForm = new FormGroup({});

        this.addressFormGroup = new FormGroup({
            zipcode: this.institutionForm.get('zipcode'),
            city: this.institutionForm.get('city'),
            address: this.institutionForm.get('address'),
        });
        this.contactsFormGroup = new FormGroup({
            phone: this.contactsFormGroup.get('phone'),
            website: this.contactsFormGroup.get('website'),
            name: this.contactsFormGroup.get('name'),
        });
    }

    ngOnInit(): void {
    }

    getOpeningHours() {
        return (this.institutionForm.get('openingHours') as FormArray);
    }

    addNewOpeningHours() {
        if (this.getOpeningHours().length < 15) {
            (this.institutionForm.get('openingHours') as FormArray).push(new FormGroup({
                'weekDay': new FormControl(null),
                'openingTime': new FormControl(null),
                'closingTime': new FormControl(null)
            }));
        }
    }

    removeOpeningHours(index: number) {
        (this.institutionForm.get('openingHours') as FormArray).removeAt(index);
    }
}
