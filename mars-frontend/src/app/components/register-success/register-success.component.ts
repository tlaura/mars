import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
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

  constructor(private activatedRoute: ActivatedRoute, private mailService: MailService) {
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
          error => console.warn(error)
        )
      }
    );
  }

}
