import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-opening-hours',
  templateUrl: './opening-hours.component.html',
  styleUrls: ['./opening-hours.component.css']
})
export class OpeningHoursComponent implements OnInit {
  weekDays: string[] = ['Hétfő', 'Kedd', 'Szerda', 'Csütörtök', 'Péntek', 'Szombat', 'Vasárnap'];

  @Output()
  openingHoursEmitter: EventEmitter<FormGroup>;

  openingHours: FormGroup;

  constructor() {
    this.openingHoursEmitter = new EventEmitter<FormGroup>();
    this.openingHours = new FormGroup({
      'weekDay': new FormControl(),
      'openingTime': new FormControl(),
      'closingTime': new FormControl()
    })
  }

  ngOnInit(): void {
  }

  emitOpeningHours() {
    this.openingHoursEmitter.emit(this.openingHours);
  }
}
