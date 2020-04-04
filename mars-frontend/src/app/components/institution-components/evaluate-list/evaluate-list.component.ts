import {Component, OnInit} from '@angular/core';
import {InstitutionListModel} from "../../../account-institution/institution/models/institutionList.model";
import {InstitutionService} from "../../../account-institution/institution/services/institution.service";

@Component({
  selector: 'app-evaluate-list',
  templateUrl: './evaluate-list.component.html',
  styleUrls: ['./evaluate-list.component.css']
})
export class EvaluateListComponent implements OnInit {

  evaluationList: Array<InstitutionListModel>;

  constructor(private institutionService: InstitutionService) {
  }

  ngOnInit(): void {
    this.getEvaluationList();
  }

  getEvaluationList = (): void => {
    this.institutionService.getEvaluationList().subscribe(
      value => this.evaluationList = value,
      error => console.warn(error)
    )
  };

  accept = (id: number): void => {
    this.sendEvaluation(true, id);
  };

  reject = (id: number): void => {
    this.sendEvaluation(false, id);
  };

  sendEvaluation = (accepted: boolean, id: number) => {
    this.institutionService.evalueateInstitution(accepted, id).subscribe(
      () => this.getEvaluationList()
    );

  }


}
