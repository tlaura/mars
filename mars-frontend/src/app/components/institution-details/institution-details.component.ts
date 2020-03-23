import {Component, Input, OnInit} from '@angular/core';
import {InstitutionDetailModel} from "../../models/institutionDetail.model";
import {ActivatedRoute, Router} from "@angular/router";
import {InstitutionService} from "../../services/institution.service";
import {OpeningHoursService} from "../../services/opening-hours.service";
import {OpeningHoursModel} from "../../models/openingHours.model";

@Component({
  selector: 'app-institution-details',
  templateUrl: './institution-details.component.html',
  styleUrls: ['./institution-details.component.css']
})
export class InstitutionDetailsComponent implements OnInit {

  @Input() institution: InstitutionDetailModel;
  @Input() mailSender: boolean = false;
  @Input() isInInfoBox: boolean = false;
  openingHours: Array<OpeningHoursModel>;
  shareUrl: string;
  shareObj = {
    //localhost-ra nem működik, elvileg élesben igen?!?!?
    href: this.shareUrl,
    hashtag: "#NONE"
  };

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private institutionService: InstitutionService, private openingHoursService: OpeningHoursService) {
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(
      paramMap => {
        const id: number = Number(paramMap.get('id'));
        if (id) {
          this.getInstitutionDetail(id);
        }
      }
    );
  }

  getInstitutionDetail = (id: number): void => {
    this.institutionService.getInstitutionDetail(id).subscribe(
      institutionDetail => this.institution = institutionDetail,
      error => console.warn(error),
      () => {
        this.getOpeningHours(id);
        //this.shareUrl = location.href + this.institution.id;
      }
    );
  };

  getOpeningHours = (id: number): void => {
    this.openingHoursService.getOpeningHoursByInstitutionId(id).subscribe(
      openingHours => this.openingHours = openingHours,
      error => console.warn(error)
    )
  };

  openMailSend = (): void => {
    this.mailSender = this.mailSender === false;
  };

  backToInstitutionList() {
    this.router.navigate(['institution-list'])
  }
}
