import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-new-password-success',
  templateUrl: './new-password-success.component.html',
  styleUrls: ['./new-password-success.component.css']
})
export class NewPasswordSuccessComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
    setTimeout(this.navigateToLogin, 2000)
  }

  private navigateToLogin = (): void => {
    this.router.navigate(["login"]);
  };
}
