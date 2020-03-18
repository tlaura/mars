import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-opening-hours',
  templateUrl: './opening-hours.component.html',
  styleUrls: ['./opening-hours.component.css']
})
export class OpeningHoursComponent implements OnInit {
  weekDays: string[] = ['Hétfő', 'Kedd', 'Szerda', 'Csütörtök', 'Péntek', 'Szombat', 'Vasárnap'];

  @Input()
  openingHours: FormGroup;

  constructor() {
    this.openingHours = new FormGroup({
      'weekDay': new FormControl(null),
      'openingTime': new FormControl(null),
      'closingTime': new FormControl(null)
    })
  }

  ngOnInit(): void {
  }


}
