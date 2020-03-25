import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {MailService} from "../../../services/mail.service";

@Component({
  selector: 'app-new-password-form',
  templateUrl: './new-password-form.component.html',
  styleUrls: ['./new-password-form.component.css']
})
export class NewPasswordFormComponent implements OnInit {

  passwordFormGroup: FormGroup;
  token: string;

  constructor(private activatedRoute: ActivatedRoute, private mailService: MailService, private router: Router) {
    this.passwordFormGroup = new FormGroup({
      password: new FormControl('')
    })
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(
      paramMap => {
        this.token = paramMap.get('token');
      }
    );
  }

  submit() {
    const password: string = this.passwordFormGroup.get('password').value;
    debugger;
    this.mailService.updatePassword(this.token, password).subscribe(
      () => {
        this.router.navigate(["new-password-success"]);
      },
      error => console.warn(error),
      () => setTimeout(this.navigateToLogin, 2000)
    )
  }

  private navigateToLogin = (): void => {
    this.router.navigate(["login"]);
  };
}
