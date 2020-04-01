import {Component, OnInit} from '@angular/core';
import {Contact} from "../../models/chat/contact";
import {Message} from "../../models/chat/message";
import {AuthenticationService} from "../../services/auth/authentication.service";
import {SocketService} from "../../services/chat/socket.service";
import {environment} from "../../../environments/environment";
import {User} from "../../models/chat/user";
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  isLoaded: boolean = false;
  isCustomSocketOpened = false;
  from: User;
  to: Contact;

  isSmall: boolean = true;
  contacts: Contact[] = [];
  messages: Message[] = [];
  isMessageWindowOpen: boolean = false;
  private serverUrl = environment.BASE_URL + '/api/chat';
  private stompClient;

  constructor(private socketService: SocketService, private authenticationService: AuthenticationService) {
    this.authenticationService.currentToken.subscribe(
      () => {
        this.from = authenticationService.getCurrentUser();
        if (!this.from) {
          this.closeMessageWindow();
          this.closeChat();
        } else {
          //TODO: fetch contacts list
          this.initializeWebSocketConnection();
        }
      }
    );

  }

  ngOnInit(): void {
  }

  changeDisplay() {
    this.isSmall = !this.isSmall;
    if (this.isSmall) {
      this.closeMessageWindow();
    }
  }

  openMessageWindow(contactId: number) {
    this.isMessageWindowOpen = true;
    this.to = this.contacts[contactId];
    this.messages = this.fetchMessages(this.from, this.to);
  }

  fetchMessages(from: User, to: Contact): Message[] {
    //TODO: fetch messages from server...
    return null;
  }

  closeMessageWindow() {
    this.isMessageWindowOpen = false;
  }

  sendMessage(message: string) {
    if (message) {
      let payload: Message = {
        fromName: this.from.name,
        fromEmail: this.from.email,
        toName: this.to.name,
        toEmail: this.to.email,
        date: new Date(),
        text: message
      };
      this.stompClient.send(this.serverUrl + "/send/message", {}, JSON.stringify(payload));
    }
  }

  openSocket() {
    if (this.isLoaded) {
      this.isCustomSocketOpened = true;
      this.stompClient.subscribe('/' + this.from.email, (message) => {
        this.handleResult(message);
      });
    }
  }

  handleResult(message) {
    if (message.body) {
      let messageResult: Message = JSON.parse(message.body);
      console.log(messageResult);
      this.messages.push(messageResult);
    }
  }

  private closeChat() {
    this.isSmall = true;
    this.contacts = [];
    this.messages = [];
  }

  private initializeWebSocketConnection() {
    let ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    let that = this;
    this.stompClient.connect({}, function (frame) {
      that.isLoaded = true;
      that.openSocket()
    });
  }
}
