import {Component, OnInit} from '@angular/core';
import {Contact} from "../../models/chat/contact";
import {Message} from "../../models/chat/message";
import {AuthenticationService} from "../../services/auth/authentication.service";
import {SocketService} from "../../services/chat/socket.service";
import {environment} from "../../../environments/environment";
import {User} from "../../models/chat/user";
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import {ContactsService} from "../../services/chat/contacts.service";

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

  fetchMessages(from: User, to: Contact): Message[] {
    this.contactsService.fetchMessages(from.email, to.email).subscribe(
      (messages: Message[]) => this.messages = messages,
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
      let payload: Message = {
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
      let messageResult: Message = JSON.parse(message.body);
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

  private fetchContacts(from: User) {
    this.contactsService.fetchContacts(from.email).subscribe(
      (contacts: Contact[]) => {
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

  private markUnreadMessages(contacts: Contact[]) {
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
