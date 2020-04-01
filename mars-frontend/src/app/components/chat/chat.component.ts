import {Component, OnInit} from '@angular/core';
import {Contact} from "../../models/chat/contact";
import {Message} from "../../models/chat/message";
import {AuthenticationService} from "../../services/auth/authentication.service";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  isSmall: boolean = true;
  contacts: Contact[] = [];
  messages: Message[] = [];
  isMessageWindowOpen: boolean = false;
  from: string = 'test';

  constructor(private authenticationService: AuthenticationService) {
    this.authenticationService.currentUser.subscribe(
      () => this.from = authenticationService.getCurrentUserEmail()
    );
    this.contacts.push(
      {name: 'Laura', email: '', online: false},
      {name: 'Peti', email: '', online: true},
      {name: 'Elza', email: '', online: false},
      {name: 'Castiel', email: '', online: true},
      {name: 'Ezekiel', email: '', online: true},
      {name: 'Gabriel', email: '', online: false},
      {name: 'Uriel', email: '', online: false},
    )
  }

  ngOnInit(): void {
  }


  changeDisplay() {
    this.isSmall = !this.isSmall;
    if (this.isSmall) {
      this.closeMessageWindow();
    }
  }

  openMessageWindow(to: string) {
    this.isMessageWindowOpen = true;
    this.messages = this.fetchMessages(this.from, to);
  }

  fetchMessages(from: string, to: string) {
    //TODO: fetch messages from server...
    return [
      {
        from: from,
        to: to,
        when: new Date(),
        text: 'Szia ' + to + ' :) '
      },
      {
        from: to,
        to: from,
        when: new Date(),
        text: 'Szia ' + from + ' :) '
      }
    ]
  }

  closeMessageWindow() {
    this.isMessageWindowOpen = false;
  }
}
