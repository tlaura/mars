import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AccountService} from "../../services/account.service";
import {ProviderUserDetails} from "../../models/providerUserDetails";

@Component({
  selector: 'app-provider-details',
  templateUrl: './provider-details.component.html',
  styleUrls: ['./provider-details.component.css']
})
export class ProviderDetailsComponent implements OnInit {

  @Input() mailSender: boolean = false;
  @Input() isInInfoBox: boolean = false;
  @Input() providerUserDetail: ProviderUserDetails;

  constructor(private activatedRoute: ActivatedRoute, private accountService: AccountService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(
      paramMap => {
        const id: number = Number(paramMap.get('id'));
        if (id) {
          this.getProviderDetails(id);
        }
      }
    );
  }

  openMailSend = (): void => {
    this.mailSender = this.mailSender === false;
  };

  backToInstitutionList() {
    this.router.navigate(['institution-list'])
  }

  private getProviderDetails = (id: number): void => {
    this.accountService.getProviderAccountDetails(id).subscribe(
      value => this.providerUserDetail = value,
      error => console.warn(error),
    )
  };

}
