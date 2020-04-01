import {Component, Input, OnInit} from '@angular/core';
import {Message} from "../../../models/chat/message";

@Component({
  selector: 'app-message-window',
  templateUrl: './message-window.component.html',
  styleUrls: ['./message-window.component.css']
})
export class MessageWindowComponent implements OnInit {

  @Input()
  messages: Message[] = [];

  constructor() {
  }

  ngOnInit(): void {
  }

}
