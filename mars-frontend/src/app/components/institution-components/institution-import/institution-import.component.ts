import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {InstitutionService} from "../../../account-institution/institution/services/institution.service";

@Component({
  selector: 'app-institution-import',
  templateUrl: './institution-import.component.html',
  styleUrls: ['./institution-import.component.css']
})
export class InstitutionImportComponent implements OnInit {

  loading = false;
  importForm: FormGroup;

  constructor(private institutionService: InstitutionService, private router: Router) {
    this.importForm = new FormGroup({
        'file': new FormControl(null, Validators.required)
      }
    )
  }

  ngOnInit(): void {
  }

  import = (): void => {
    let importData: any = new FormData();
    importData.append("file", this.importForm.get('file').value);
    this.loading = true;
    this.institutionService.import(importData).subscribe(
      () => {
        this.router.navigate(['institution-import-complete']);
      },
      error => {
        console.warn(error);
      },
      () => {
        console.log('Sikeres importálás.');
        this.loading = false;
      }
    )
  };

  //TODO: fix DOMException ERROR

  uploadFile = (event): void => {

    const file = (event.target as HTMLInputElement).files[0];
    this.importForm.patchValue({
      file: file
    });
    this.importForm.get('file').updateValueAndValidity()
  };

}
