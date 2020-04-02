import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Message} from "../../../models/chat/message";
import {User} from "../../../models/chat/user";

@Component({
  selector: 'app-message-window',
  templateUrl: './message-window.component.html',
  styleUrls: ['./message-window.component.css']
})
export class MessageWindowComponent implements OnInit {

  @Input()
  messages: Message[] = [];
  @Input()
  currentUser: User;
  @Output()
  messageEmitter: EventEmitter<string> = new EventEmitter<string>();
  message: string;

  constructor() {
  }

  ngOnInit(): void {

  }

  sendMessage() {
    this.messageEmitter.emit(this.message);
    this.message = '';
  }


}
