import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";
import {MailService} from "../../../../services/mail.service";

@Component({
  selector: 'app-new-password',
  templateUrl: './new-password.component.html',
  styleUrls: ['./new-password.component.css']
})
export class NewPasswordComponent implements OnInit {

  emailFormGroup: FormGroup;

  constructor(private mailService: MailService, private router: Router) {
    this.emailFormGroup = new FormGroup({
      email: new FormControl()
    })
  }

  ngOnInit(): void {
  }

  submit() {
    const emailData: string = this.emailFormGroup.get('email').value;
    this.mailService.sendNewPasswordRequest(emailData).subscribe(
      () => {
        this.router.navigate(['new-password-complete'])
      }
    )
  }
}
