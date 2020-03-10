import {Component, Input, OnInit} from '@angular/core';
import {institutionListIndex} from "../../../environments/institutionListIndex.prod";
import {AgmInfoWindow, MapsAPILoader} from "@agm/core";
import {GeoLocationModel} from "../../models/geoLocation.model";
import {InstitutionService} from "../../services/institution.service";
import {InstitutionDetailModel} from "../../models/institutionDetail.model";

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  latitude: number = institutionListIndex.mapLatitude;
  longitude: number = institutionListIndex.mapLongitude;
  zoom: number = institutionListIndex.mapZoom;
  showDetails: boolean = false;
  infoWindow: AgmInfoWindow;

  @Input() locations: Array<GeoLocationModel> = [];
  institutionDetail: InstitutionDetailModel;

  constructor(private mapsAPILoader: MapsAPILoader, private institutionService: InstitutionService) {
  }

  ngOnInit(): void {
    this.mapsAPILoader.load().then(() => this.setCurrentLocation());
  }

  setCurrentLocation = () => {
    if ('geolocation' in navigator) {
      navigator.geolocation.getCurrentPosition(position => {
        this.latitude = position.coords.latitude;
        this.longitude = position.coords.longitude;
        this.zoom = institutionListIndex.mapZoom;
      })
    }
  };

  markerClick = (infoWindow: AgmInfoWindow, id: number) => {
    if (this.infoWindow) {
      this.infoWindow.close();
    }
    this.institutionService.getInstitutionDetail(id).subscribe(
      institutionDetail => this.institutionDetail = institutionDetail,
      error => console.warn(error),
      () => {
        this.infoWindow = infoWindow;
        infoWindow.open();
      }
    );

  };

}
