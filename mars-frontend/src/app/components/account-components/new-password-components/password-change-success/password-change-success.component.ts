import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-password-change-success',
  templateUrl: './password-change-success.component.html',
  styleUrls: ['./password-change-success.component.css']
})
export class PasswordChangeSuccessComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
    setTimeout(this.navigateToProfile, 2000)
  }

  navigateToProfile = () => {
    this.router.navigate(['my-profile']);
  }
}
