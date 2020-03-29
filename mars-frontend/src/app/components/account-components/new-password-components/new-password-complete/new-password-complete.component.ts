import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-new-password-complete',
  templateUrl: './new-password-complete.component.html',
  styleUrls: ['./new-password-complete.component.css']
})
export class NewPasswordCompleteComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
    setTimeout(this.navigateToLogin, 2000)
  }

  private navigateToLogin = (): void => {
    this.router.navigate(["login"]);
  };
}
