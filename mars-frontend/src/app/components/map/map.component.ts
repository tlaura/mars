import {Component, Input, OnInit} from '@angular/core';
import {institutionListIndex} from "../../../environments/institutionListIndex.prod";
import {MapsAPILoader} from "@agm/core";
import {InstitutionService} from "../../services/institution.service";
import {InstitutionDetailModel} from "../../models/institutionDetail.model";
import {AccountInstitutionListModel} from "../../models/accountInstitutionList.model";
import {AccountService} from "../../services/account.service";
import {ProviderUserDetalsModel} from "../../models/providerUserDetals.model";


@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  showMap: boolean = false;
  showInfoWindow: boolean = false;
  latitude: number = institutionListIndex.mapLatitude;
  longitude: number = institutionListIndex.mapLongitude;
  zoom: number = institutionListIndex.mapZoom;
  @Input() showInstitutionDetails: boolean = false;
  @Input() showProviderDetail: boolean = false;
  @Input() locations: Array<AccountInstitutionListModel>;
  institutionDetail: InstitutionDetailModel;
  providerDetail: ProviderUserDetalsModel;

  constructor(private mapsAPILoader: MapsAPILoader,
              private institutionService: InstitutionService,
              private accountService: AccountService) {
  }


  ngOnInit(): void {
    this.mapsAPILoader.load().then(() => {
        this.setCurrentLocation();
        this.showMap = true;
      }
    );
  }

  setCurrentLocation = () => {

    if ('geolocation' in navigator) {
      navigator.geolocation.getCurrentPosition(position => {
        //  this.latitude = position.coords.latitude;
        //  this.longitude = position.coords.longitude;
        this.zoom = institutionListIndex.mapZoom;
      })
    }
  };

  markerClick = (index: number) => {
    let id: number = this.locations[index].id;
    let accountType = this.locations[index].accountType;
    switch (accountType) {
      case 'PROVIDER':
        this.getProviderDetails(id);
        break;
      case 'INSTITUTION':
        this.getInstitutionDetail(id);
        break;
      case 'INSTITUTION_WITH_PROVIDER':

        break;
    }
  };

  private getInstitutionDetail = (id: number): void => {
    this.institutionService.getInstitutionDetail(id).subscribe(
      value => this.institutionDetail = value,
      error => console.warn(error),
      () => {
        this.showInstitutionDetails = true;
        this.showProviderDetail = false;
        this.showInfoWindow = true;
      }
    );
  };


  private getProviderDetails = (id: number): void => {
    this.accountService.getProviderAccountDetails(id).subscribe(
      value => this.providerDetail = value,
      error => console.warn(error),
      () => {
        this.showProviderDetail = true;
        this.showInstitutionDetails = false;
        this.showInfoWindow = true;
      }
    );
  };

  icon_institution = {
    url: 'https://svgshare.com/i/JPk.svg',
    scaledSize: {
      width: 40,
      height: 40
    }
  };

  icon_provider = {
    url: 'https://svgur.com/s/JP2.',
    scaledSize: {
      width: 40,
      height: 40
    }
  };

  getIcon() {
    //TODO: choose right icon
    return this.icon_institution;
  }
}
