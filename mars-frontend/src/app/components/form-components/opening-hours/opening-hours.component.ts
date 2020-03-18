import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Time} from "@angular/common";
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-opening-hours',
  templateUrl: './opening-hours.component.html',
  styleUrls: ['./opening-hours.component.css']
})
export class OpeningHoursComponent implements OnInit {
  weekDays: string[] = ['Hétfő', 'Kedd', 'Szerda', 'Csütörtök', 'Péntek', 'Szombat', 'Vasárnap'];

  @Output()
  weekDayEmitter: EventEmitter<string>;
  @Output()
  openingTimeEmitter: EventEmitter<Time>;
  @Output()
  closingTimeEmitter: EventEmitter<Time>;

  weekDay: FormControl;
  openingTime: FormControl;
  closingTime: FormControl;

  constructor() {
    this.weekDay = new FormControl();
    this.openingTime = new FormControl();
    this.closingTime = new FormControl();
    this.weekDayEmitter = new EventEmitter<string>();
    this.openingTimeEmitter = new EventEmitter<Time>();
    this.closingTimeEmitter = new EventEmitter<Time>();
  }

  ngOnInit(): void {
  }

  emitWeekDay() {
    this.weekDayEmitter.emit(this.weekDay.value);
  }

  emitOpeningTime() {
    this.openingTimeEmitter.emit(this.openingTime.value);
  }

  emitClosingTime() {
    this.closingTimeEmitter.emit(this.closingTime.value);
  }
}
