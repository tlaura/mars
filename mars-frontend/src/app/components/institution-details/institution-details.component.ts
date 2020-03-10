import {Component, Input, OnInit} from '@angular/core';
import {InstitutionDetailModel} from "../../models/institutionDetail.model";

@Component({
  selector: 'app-institution-details',
  templateUrl: './institution-details.component.html',
  styleUrls: ['./institution-details.component.css']
})
export class InstitutionDetailsComponent implements OnInit {

  @Input() institution: InstitutionDetailModel;

  constructor() {
  }

  ngOnInit() {
  }

}
