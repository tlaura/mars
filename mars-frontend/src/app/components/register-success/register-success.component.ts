import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {MailService} from "../../services/mail.service";

@Component({
  selector: 'app-register-success',
  templateUrl: './register-success.component.html',
  styleUrls: ['./register-success.component.css']
})
export class RegisterSuccessComponent implements OnInit {

  confirmed: boolean = false;

  constructor(private activatedRoute: ActivatedRoute, private mailService: MailService) {
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
