import {Component, Input, OnInit} from '@angular/core';
import {InstitutionDetailModel} from "../../models/institutionDetail.model";
import {ActivatedRoute} from "@angular/router";
import {InstitutionService} from "../../services/institution.service";

@Component({
  selector: 'app-institution-details',
  templateUrl: './institution-details.component.html',
  styleUrls: ['./institution-details.component.css']
})
export class InstitutionDetailsComponent implements OnInit {

  @Input() institution: InstitutionDetailModel;
  @Input() mailSender: boolean = false;

  constructor(private activatedRoute: ActivatedRoute, private institutionService: InstitutionService) {
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
      paramMap => {
        const id: number = Number(paramMap.get('id'));
        if (id) {
          this.institutionService.getInstitutionDetail(id).subscribe(
            institutionDetail => this.institution = institutionDetail,
            error => console.warn(error)
          )
        }
      }
    )
  }

  openMailSend = () => {
    this.mailSender = true;
  };
  closeMailSender = () => {
    this.mailSender = false;
  }

}
