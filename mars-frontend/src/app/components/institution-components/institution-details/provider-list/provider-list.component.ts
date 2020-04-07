import {Component, Input, OnInit} from '@angular/core';
import {ProviderUserShortDetailsModel} from "../../../../account-institution/account/models/providerUserShortDetails.model";

@Component({
  selector: 'app-provider-list',
  templateUrl: './provider-list.component.html',
  styleUrls: ['./provider-list.component.css']
})
export class ProviderListComponent implements OnInit {

  @Input()
  providers: ProviderUserShortDetailsModel[] = [];
  isSmall: boolean[] = [];

  constructor() {
  }

  ngOnInit(): void {
    this.providers.forEach(() => this.isSmall.push(true))
  }

}
