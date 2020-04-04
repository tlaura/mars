import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {institutionListIndex} from "../../../environments/institutionListIndex.prod";
import {MapsAPILoader} from "@agm/core";
import {InstitutionService} from "../../account-institution/institution/services/institution.service";
import {InstitutionDetailModel} from "../../account-institution/institution/models/institutionDetail.model";
import {AccountService} from "../../account-institution/account/services/account.service";
import {ProviderUserDetails} from "../../account-institution/account/models/providerUserDetails";
import {AccountInstitutionListModel} from "../../account-institution/models/accountInstitutionList.model";
import {LocationRangeModel} from "../../account-institution/models/locationRange.model";


@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  styles = [
    {
      "elementType": "geometry",
      "stylers": [
        {
          "color": "#f5f5f5"
        }
      ]
    },
    {
      "elementType": "labels.icon",
      "stylers": [
        {
          "visibility": "off"
        }
      ]
    },
    {
      "elementType": "labels.text.fill",
      "stylers": [
        {
          "color": "#616161"
        }
      ]
    },
    {
      "elementType": "labels.text.stroke",
      "stylers": [
        {
          "color": "#f5f5f5"
        }
      ]
    },
    {
      "featureType": "administrative.land_parcel",
      "elementType": "labels.text.fill",
      "stylers": [
        {
          "color": "#bdbdbd"
        }
      ]
    },
    {
      "featureType": "poi",
      "elementType": "geometry",
      "stylers": [
        {
          "color": "#eeeeee"
        }
      ]
    },
    {
      "featureType": "poi",
      "elementType": "labels.text.fill",
      "stylers": [
        {
          "color": "#757575"
        }
      ]
    },
    {
      "featureType": "poi.park",
      "elementType": "geometry",
      "stylers": [
        {
          "color": "#e5e5e5"
        }
      ]
    },
    {
      "featureType": "poi.park",
      "elementType": "labels.text.fill",
      "stylers": [
        {
          "color": "#9e9e9e"
        }
      ]
    },
    {
      "featureType": "road",
      "elementType": "geometry",
      "stylers": [
        {
          "color": "#ffffff"
        }
      ]
    },
    {
      "featureType": "road.arterial",
      "elementType": "labels.text.fill",
      "stylers": [
        {
          "color": "#757575"
        }
      ]
    },
    {
      "featureType": "road.highway",
      "elementType": "geometry",
      "stylers": [
        {
          "color": "#dadada"
        }
      ]
    },
    {
      "featureType": "road.highway",
      "elementType": "labels.text.fill",
      "stylers": [
        {
          "color": "#616161"
        }
      ]
    },
    {
      "featureType": "road.local",
      "elementType": "labels.text.fill",
      "stylers": [
        {
          "color": "#9e9e9e"
        }
      ]
    },
    {
      "featureType": "transit.line",
      "elementType": "geometry",
      "stylers": [
        {
          "color": "#e5e5e5"
        }
      ]
    },
    {
      "featureType": "transit.station",
      "elementType": "geometry",
      "stylers": [
        {
          "color": "#eeeeee"
        }
      ]
    },
    {
      "featureType": "water",
      "elementType": "geometry",
      "stylers": [
        {
          "color": "#c9c9c9"
        }
      ]
    },
    {
      "featureType": "water",
      "elementType": "labels.text.fill",
      "stylers": [
        {
          "color": "#9e9e9e"
        }
      ]
    }
  ];


  showMap: boolean = false;
  showInfoWindow: boolean = false;
  latitude: number = institutionListIndex.mapLatitude;
  longitude: number = institutionListIndex.mapLongitude;
  zoom: number = institutionListIndex.mapZoom;
  @Input()
  showInstitutionDetails: boolean = false;
  @Input()
  showProviderDetail: boolean = false;
  @Input()
  locations: Array<AccountInstitutionListModel>;
  institutionDetail: InstitutionDetailModel;
  providerDetail: ProviderUserDetails;
  @Output()
  locationRangeEmitter: EventEmitter<LocationRangeModel> = new EventEmitter<LocationRangeModel>();

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
        let locationRange: LocationRangeModel = new class implements LocationRangeModel {
          latitude: number;
          longitude: number;
          range: number;
        };
        locationRange.latitude = position.coords.latitude;
        locationRange.longitude = position.coords.longitude;
        this.locationRangeEmitter.emit(locationRange);
        //  this.latitude = position.coords.latitude;
        //  this.longitude = position.coords.longitude;
        this.zoom = institutionListIndex.mapZoom;
      })
    }
  };

  markerClick = (institution: AccountInstitutionListModel) => {
    let accountType = institution.accountType;
    if (accountType === 'PROVIDER') {
      this.getProviderDetails(institution.id);
    } else {
      this.getInstitutionDetail(institution.id);
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
    url: 'https://svgshare.com/i/JP2.svg',
    scaledSize: {
      width: 40,
      height: 40
    }
  };

  getIcon(listItem: AccountInstitutionListModel) {
    if (listItem.accountType == 'PROVIDER') {
      return this.icon_provider;
    } else {
      return this.icon_institution;
    }
  }

}
