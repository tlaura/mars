import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-institution-import-complete',
  templateUrl: './institution-import-complete.component.html',
  styleUrls: ['./institution-import-complete.component.css']
})
export class InstitutionImportCompleteComponent implements OnInit {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
    setTimeout(() => {
      this.router.navigate(['']);
    }, 3000);
  }

}
