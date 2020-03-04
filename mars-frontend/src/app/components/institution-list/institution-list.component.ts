import {Component, OnInit} from '@angular/core';
import {InstitutionListItemModel} from "../../models/institutionListItem.model";
import {InstitutionService} from "../../services/institution.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-institution-list',
  templateUrl: './institution-list.component.html',
  styleUrls: ['./institution-list.component.css']
})
export class InstitutionListComponent implements OnInit {

  institutionList: Array<InstitutionListItemModel>;

  constructor(private institutionService: InstitutionService,
              private router: Router) {
  }

  ngOnInit() {
    this.institutionService.loadInstitutions().subscribe(
      institutionList => this.institutionList = institutionList
    );
  }

  details(id: number) {
    this.router.navigate(["institution-details", id]);
  }
}
