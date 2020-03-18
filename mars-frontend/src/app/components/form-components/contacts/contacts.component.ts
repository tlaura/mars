import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.css']
})
export class ContactsComponent implements OnInit {
  PHONE_REGEX = '^\\+(\\d{1,2})\\D*(\\d{1,3})\\D*(\\d{3})\\D*(\\d{3,4})$';
  WEBSITE_REGEX = '(https?:\\/\\/)?(www\\.)[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,4}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)|(https?:\\/\\/)?(www\\.)?(?!ww)[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,4}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)';
  NAME_REGEX = '^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.\'-]+$';

  @Output()
  phoneEmitter: EventEmitter<string>;
  @Output()
  websiteEmitter: EventEmitter<string>;
  @Output()
  nameEmitter: EventEmitter<string>;

  @Output()
  emailEmitter: EventEmitter<string>;

  phone: FormControl;
  website: FormControl;
  name: FormControl;

  constructor() {
    this.phone = new FormControl('', Validators.pattern(this.PHONE_REGEX));
    this.website = new FormControl('', Validators.pattern(this.WEBSITE_REGEX));
    this.name = new FormControl('', Validators.pattern(this.NAME_REGEX));
    this.phoneEmitter = new EventEmitter<string>();
    this.emailEmitter = new EventEmitter<string>();
    this.websiteEmitter = new EventEmitter<string>();
    this.nameEmitter = new EventEmitter<string>();
  }

  ngOnInit(): void {
  }

  emitPhone() {
    this.phoneEmitter.emit(this.phone.value);
  }

  emitWebsite() {
    this.websiteEmitter.emit(this.website.value);
  }

  emitName() {
    this.nameEmitter.emit(this.name.value);
  }

  emitEmail(email: string) {
    this.emailEmitter.emit(email);
  }
}
