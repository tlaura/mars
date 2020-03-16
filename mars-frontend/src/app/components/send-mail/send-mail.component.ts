import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {MailService} from "../../services/mail.service";
import {EmailModel} from "../../models/email.model";

@Component({
  selector: 'app-send-mail',
  templateUrl: './send-mail.component.html',
  styleUrls: ['./send-mail.component.css']
})
export class SendMailComponent implements OnInit {

  emailForm: FormGroup;
  @Input() toEmail: string;
  @Output() sent = new EventEmitter();

  constructor(private mailService: MailService) {
    this.emailForm = new FormGroup({
      fromEmail: new FormControl(''),
      name: new FormControl(''),
      subject: new FormControl(''),
      text: new FormControl('')
    })
  }

  ngOnInit(): void {
  }


  sendMail() {
    let mailModel: EmailModel = this.emailForm.value;
    mailModel.toEmail = this.toEmail;
    this.mailService.sendEmail(mailModel).subscribe(
      () => this.sent.emit(),
    );
  }
}
