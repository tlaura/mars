import {Component, OnInit} from '@angular/core';
import {InstitutionService} from "../../services/institution.service";
import {Router} from "@angular/router";
import {InstitutionListModel} from "../../models/institutionList.model";
import {InstitutionTypeModel} from "../../models/InstitutionType.model";
import {institutionListIndex} from "../../../environments/institutionListIndex.prod";
import {GeocodeService} from "../../services/geocode.service";
import {GeoLocationModel} from "../../models/geoLocation.model";

@Component({
  selector: 'app-institution-list',
  templateUrl: './institution-list.component.html',
  styleUrls: ['./institution-list.component.css']
})
export class InstitutionListComponent implements OnInit {

  institutionList: Array<InstitutionListModel>;
  institutionTypeList: Array<InstitutionTypeModel>;
  page: number = institutionListIndex.startPageIndex;
  size: number = institutionListIndex.numberOfItemPerPage;
  searchText: string;
  locations: Array<GeoLocationModel> = [];

  sizeArray: Array<number> = institutionListIndex.itemsPerPageArray;

  constructor(private institutionService: InstitutionService,
              private router: Router,
              private geocodeService: GeocodeService) {
  }

  setSize = (size: number) => {
    this.size = size;
    this.page = institutionListIndex.startPageIndex;
    this.getInstitutions();
  };

  ngOnInit() {
    this.getInstitutions();
    this.getInstitutionType();
  }

  narrowByType = (type: string) => {
    //todo refactor magic string
    if (type !== "all") {
      this.institutionService.getInstitutionByType(type).subscribe(
        institutionList => this.institutionList = institutionList,
        error => console.warn(error),
        () => {
          this.locations = [];
          this.institutionList.forEach(this.initGeoArray);
        }
      );
    } else {
      this.getInstitutions();
    }
  };

  private getInstitutionType = () => {
    this.institutionService.getInstitutionTypes().subscribe(
      institutionTypeList => this.institutionTypeList = institutionTypeList,
      error => console.warn(error)
    );
  };

  private getInstitutions = () => {
    this.institutionService.getInstitutionList().subscribe(
      institutionList => this.institutionList = institutionList,
      error => console.warn(error),
      () => this.institutionList.forEach(this.getGeoCode)
    );
  };

  private getGeoCode = (listItem: InstitutionListModel): void => {
    if (listItem.longitude === null || listItem.latitude === null) {
      let address: string = listItem.zipCode + " " + listItem.city + " " + listItem.address;
      this.geocodeService.getLocations(address).subscribe(
        value => {
          listItem.latitude = value.results[0].geometry.location.lat;
          listItem.longitude = value.results[0].geometry.location.lng;
        },
        error => console.warn(error),
        () => {
          this.updateInstitutionLocation(listItem);
          this.initGeoArray(listItem);

        }
      );
    } else {
      this.initGeoArray(listItem);
    }
  };

  private updateInstitutionLocation(listItem: InstitutionListModel) {
    let geoLocation: GeoLocationModel = {
      id: listItem.id,
      latitude: listItem.latitude,
      longitude: listItem.longitude
    };
    this.institutionService.updateInstitutionLocation(geoLocation, listItem.id).subscribe();
  }

  private initGeoArray = (listItem: InstitutionListModel): void => {
    let location: GeoLocationModel = {
      id: listItem.id,
      latitude: listItem.latitude,
      longitude: listItem.longitude
    };
    this.locations.push(location);
  };

  details = (id: number) => {
    this.router.navigate(["institution-details", id]);
  };
}
