import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { InstitutionFormComponent } from './components/institution-form/institution-form.component';
import { InstitutionListComponent } from './components/institution-list/institution-list.component';
import { InstitutionDetailsComponent } from './components/institution-details/institution-details.component';
import { UserFormComponent } from './components/user-form/user-form.component';

@NgModule({
  declarations: [
    AppComponent,
    InstitutionFormComponent,
    InstitutionListComponent,
    InstitutionDetailsComponent,
    UserFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
