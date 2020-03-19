import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MailService} from "../../services/mail.service";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-register-success',
  templateUrl: './register-success.component.html',
  styleUrls: ['./register-success.component.css']
})
export class RegisterSuccessComponent implements OnInit {

  confirmed: boolean = false;
  form: FormGroup;

  constructor(private activatedRoute: ActivatedRoute, private mailService: MailService, private router: Router) {
    this.form = new FormGroup({
      confirmed: new FormControl('')
    });
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(
      paramMap => {
        const token: string = paramMap.get('token');
        this.mailService.confirmRegistration(token).subscribe(
          () => this.confirmed = true,
          error => console.warn(error),
          () => setTimeout(this.navigateToLogin, 30000)
        )
      }
    );
  };

  private navigateToLogin = (): void => {
    this.router.navigate(["login"]);
  };

}
