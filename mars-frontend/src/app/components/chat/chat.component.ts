import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  isSmall: boolean = true;

  constructor() {
  }

  ngOnInit(): void {
  }


  changeDisplay() {
    this.isSmall = !this.isSmall;
  }
}
