import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {InstitutionFormComponent} from './components/institution-form/institution-form.component';
import {InstitutionListComponent} from './components/institution-list/institution-list.component';
import {InstitutionDetailsComponent} from './components/institution-details/institution-details.component';
import {UserFormComponent} from './components/user-form/user-form.component';
import {NavbarComponent} from "./components/navbar/navbar.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {RegisterComponent} from './components/register/register.component';
import {Ng2SearchPipeModule} from "ng2-search-filter";
import {NgxPaginationModule} from "ngx-pagination";
import {AgmCoreModule} from "@agm/core";
import {environment} from "../environments/environment.local";
import {MapComponent} from './components/map/map.component';
import {LoginFormComponent} from "./components/login-form/login-form.component";
import {HttpRequestInterceptor} from "./utils/httpRequestInterceptor";

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    InstitutionFormComponent,
    InstitutionListComponent,
    InstitutionDetailsComponent,
    UserFormComponent,
    RegisterComponent,
    LoginFormComponent,
    MapComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    Ng2SearchPipeModule,
    FormsModule,
    NgxPaginationModule,
    AgmCoreModule.forRoot({
      apiKey: environment.apiKey,
      libraries: ["places", "geometry"]
    })
  ],
  providers: [
    [
      { provide: HTTP_INTERCEPTORS, useClass: HttpRequestInterceptor, multi: true }
    ],
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
