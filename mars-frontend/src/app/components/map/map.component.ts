import {Component, Input, OnInit} from '@angular/core';
import {institutionListIndex} from "../../../environments/institutionListIndex.prod";
import {MapsAPILoader} from "@agm/core";
import {InstitutionService} from "../../services/institution.service";
import {InstitutionDetailModel} from "../../models/institutionDetail.model";
import {InstitutionListModel} from "../../models/institutionList.model";


@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  latitude: number = institutionListIndex.mapLatitude;
  longitude: number = institutionListIndex.mapLongitude;
  zoom: number = institutionListIndex.mapZoom;
  @Input() show: boolean = false;
  @Input() locations: Array<InstitutionListModel>;
  institutionDetail: InstitutionDetailModel;

  constructor(private mapsAPILoader: MapsAPILoader, private institutionService: InstitutionService) {
  }


  ngOnInit(): void {
    this.mapsAPILoader.load().then(() => this.setCurrentLocation()
    );
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

  markerClick = (id: number) => {
    this.institutionService.getInstitutionDetail(id).subscribe(
      institutionDetail => this.institutionDetail = institutionDetail,
      error => console.warn(error),
      () => {
        this.show = true;
      }
    );

  };

  icon_institution = {
    url: 'https://svgur.com/s/JPk',
    scaledSize: {
      width: 40,
      height: 40
    }
  };

  icon_provider = {
    url: 'https://svgur.com/s/JP2',
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
