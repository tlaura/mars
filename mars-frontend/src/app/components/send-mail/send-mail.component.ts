import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MailService} from "../../services/mail.service";
import {EmailModel} from "../../models/email.model";

@Component({
  selector: 'app-send-mail',
  templateUrl: './send-mail.component.html',
  styleUrls: ['./send-mail.component.css']
})
export class SendMailComponent implements OnInit {
  EMAIL_REGEX = '(?:[a-z0-9!#$%&\'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&\'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\\])';

  emailForm: FormGroup;
  @Input() toEmail: string;
  @Output() sent = new EventEmitter();

  constructor(private mailService: MailService) {
    this.emailForm = new FormGroup({
      fromEmail: new FormControl('', [Validators.required, Validators.pattern(this.EMAIL_REGEX)]),
      name: new FormControl('', Validators.required),
      subject: new FormControl('', Validators.required),
      text: new FormControl('', Validators.required)
    })
  }

  //TODO: validator for email, name, message length...
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
