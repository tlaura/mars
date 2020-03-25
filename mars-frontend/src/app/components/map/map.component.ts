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
  latitude: number = institutionListIndex.mapLatitude;
  longitude: number = institutionListIndex.mapLongitude;
  zoom: number = institutionListIndex.mapZoom;
  @Input() show: boolean = false;
  @Input() locations: Array<InstitutionListModel>;
  institutionDetail: InstitutionDetailModel;

  constructor(private mapsAPILoader: MapsAPILoader, private institutionService: InstitutionService) {
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
        this.latitude = position.coords.latitude;
        this.longitude = position.coords.longitude;
        this.zoom = institutionListIndex.mapZoom;
      })
    }
  };

  markerClick = (index: number) => {
    let id: number = this.locations[index].id;
    this.institutionService.getInstitutionDetail(id).subscribe(
      institutionDetail => {
        this.institutionDetail = institutionDetail;
        this.latitude = this.locations[index].latitude;
        this.longitude = this.locations[index].longitude;
      },
      error => console.warn(error),
      () => {
        this.show = true;
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
