import {Component, Input, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.css']
})
export class ContactsComponent implements OnInit {
  PHONE_REGEX = '^\\+(\\d{1,2})\\D*(\\d{1,3})\\D*(\\d{3})\\D*(\\d{3,4})$';
  WEBSITE_REGEX = '(https?:\\/\\/)?(www\\.)[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,4}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)|(https?:\\/\\/)?(www\\.)?(?!ww)[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,4}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)';
  NAME_REGEX = '^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.\'-]+$';


  @Input() contactsFormGroup: FormGroup = new FormGroup({});

  constructor() {

  }

  ngOnInit(): void {
  }

}
