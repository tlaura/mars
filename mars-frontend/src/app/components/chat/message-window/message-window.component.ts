import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MessageModel} from "../../../chat/models/message.model";
import {UserModel} from "../../../chat/models/user.model";

@Component({
  selector: 'app-message-window',
  templateUrl: './message-window.component.html',
  styleUrls: ['./message-window.component.css']
})
export class MessageWindowComponent implements OnInit {

  @Input()
  messages: MessageModel[] = [];
  @Input()
  currentUser: UserModel;
  @Output()
  messageEmitter: EventEmitter<string> = new EventEmitter<string>();
  message: string;
  isScrolled: boolean = false;

  constructor() {
  }

  ngOnInit(): void {

  }

  sendMessage() {
    this.messageEmitter.emit(this.message);
    this.message = '';
  }

  scrollDown() {
    const element = document.getElementById("messages");
    if (element) {
      element.scrollTop = element.scrollHeight;
    }
    this.isScrolled = false;
  }
}
