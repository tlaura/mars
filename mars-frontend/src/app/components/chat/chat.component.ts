import {Component, OnInit} from '@angular/core';
import {ContactModel} from "../../chat/models/contact.model";
import {MessageModel} from "../../chat/models/message.model";
import {AuthenticationService} from "../../auth/services/authentication.service";
import {SocketService} from "../../chat/services/socket.service";
import {environment} from "../../../environments/environment";
import {UserModel} from "../../chat/models/user.model";
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import {ContactsService} from "../../chat/services/contacts.service";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  isLoaded: boolean = false;
  isCustomSocketOpened = false;
  from: UserModel;
  to: ContactModel;

  isSmall: boolean = true;
  contacts: ContactModel[] = [];
  messages: MessageModel[] = [];
  unreadMessages: boolean[] = [];
  isMessageWindowOpen: boolean = false;
  private serverUrl = environment.BASE_URL + '/api';
  private stompClient;

  constructor(private socketService: SocketService, private authenticationService: AuthenticationService, private contactsService: ContactsService) {

  }

  ngOnInit(): void {
    this.authenticationService.currentToken.subscribe(
      () => {
        this.from = this.authenticationService.getCurrentUser();
        if (!this.from) {
          this.closeMessageWindow();
          this.closeChat();
        } else {
          this.fetchContacts(this.from);
          this.initializeWebSocketConnection();
        }
      }
    );
    this.contactsService.contactSubject.subscribe(
      () => {
        this.fetchContacts(this.from);
        this.isSmall = false;
      },
      (error) => console.log(error)
    )
  }

  changeDisplay() {
    this.isSmall = !this.isSmall;
    if (this.isSmall) {
      this.closeMessageWindow();
    }
  }

  openMessageWindow(contactId: number) {
    this.messages = [];
    this.unreadMessages[contactId] = false;
    this.isMessageWindowOpen = true;
    this.to = this.contacts[contactId];
    this.messages = this.fetchMessages(this.from, this.to);
  }

  fetchMessages(from: UserModel, to: ContactModel): MessageModel[] {
    this.contactsService.fetchMessages(from.email, to.email).subscribe(
      (messages: MessageModel[]) => this.messages = messages,
      (error) => console.log(error),
      () => this.scrollDown()
    );
    return null;
  }

  closeMessageWindow() {
    this.isMessageWindowOpen = false;
    this.messages = [];
  }

  sendMessage(message: string) {
    if (message) {
      let payload: MessageModel = {
        fromName: this.from.name,
        fromEmail: this.from.email,
        toName: this.to.name,
        toEmail: this.to.email,
        date: new Date(),
        text: message
      };
      this.stompClient.send("/chat/send/message", {}, JSON.stringify(payload));
    }
  }

  openSocket() {
    if (this.isLoaded) {
      this.isCustomSocketOpened = true;
      this.stompClient.subscribe('/topic/' + this.from.email, (message) => {
        this.handleResult(message);
        console.log('New message');
        this.fetchContacts(this.from);
      });
    }
  }

  handleResult(message) {
    if (message.body) {
      let messageResult: MessageModel = JSON.parse(message.body);
      this.fetchContacts(this.from);
      if ((messageResult.fromEmail == this.to.email && messageResult.toEmail == this.from.email) ||
        (messageResult.fromEmail == this.from.email && messageResult.toEmail == this.to.email)) {
        this.messages.push(messageResult);
        this.scrollDown();
      } else {
        let otherEmail = messageResult.fromEmail;
        let otherContact = this.contacts.find(contact => contact.email == otherEmail);
        let index = this.contacts.indexOf(otherContact);
        this.unreadMessages[index] = true;
      }
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

  private fetchContacts(from: UserModel) {
    this.contactsService.fetchContacts(from.email).subscribe(
      (contacts: ContactModel[]) => {
        if (contacts.length > this.contacts.length) {
          this.isSmall = false;
          this.markUnreadMessages(contacts);
        }
        this.contacts = contacts;
        console.log('Contacts listed.')
      },
      (error) => console.log(error)
    )
  }

  private markUnreadMessages(contacts: ContactModel[]) {
    contacts.filter(contact => !this.contacts.includes(contact))
      .forEach(() => this.unreadMessages.push(true))
  }


  scrollDown() {
    setTimeout(() => {
      const element = document.getElementById("messages");
      if (element) {
        element.scrollTop = element.scrollHeight;
      }
    }, 100)
  }

}
