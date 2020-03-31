import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-deletion-success',
  templateUrl: './deletion-success.component.html',
  styleUrls: ['./deletion-success.component.css']
})
export class DeletionSuccessComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
    setTimeout(this.navigateToRegister, 2000)
  }

  private navigateToRegister = (): void => {
    this.router.navigate(["login"]);
  };
}
