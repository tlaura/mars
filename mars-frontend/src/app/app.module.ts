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
import {AgmCoreModule, GoogleMapsAPIWrapper} from "@agm/core";
import {environment} from "../environments/environment.local";
import {MapComponent} from './components/map/map.component';
import {LoginFormComponent} from "./components/login-form/login-form.component";
import {HttpRequestInterceptor} from "./utils/httpRequestInterceptor";
import {RegistrationCompleteComponent} from './components/registration-complete/registration-complete.component';
import {RegisterSuccessComponent} from './components/register-success/register-success.component';
import {MyProfileComponent} from './components/my-profile/my-profile.component';
import {InstitutionImportComponent} from './components/institution-import/institution-import.component';
import {SendMailComponent} from './components/send-mail/send-mail.component';
import {AgmSnazzyInfoWindowModule} from "@agm/snazzy-info-window";
import {NgxSocialButtonModule, SocialServiceConfig} from "ngx-social-button";
import {EmailComponent} from './components/form-components/email/email.component';
import {ContactsComponent} from './components/form-components/contacts/contacts.component';
import {AddressComponent} from './components/form-components/address/address.component';
import {ProviderAttributesComponent} from './components/form-components/provider-attributes/provider-attributes.component';
import {PasswordComponent} from './components/form-components/password/password.component';

export function getAuthServiceConfigs() {
  let config = new SocialServiceConfig()
    .addFacebook("229963898396055")
    .addGoogle("Your-Google-Client-Id")
    .addLinkedIn("Your-LinkedIn-Client-Id");
  return config;
}

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
    RegisterSuccessComponent,
    RegistrationCompleteComponent,
    MyProfileComponent,
    SendMailComponent,
    MyProfileComponent,
    InstitutionImportComponent,
    EmailComponent,
    ContactsComponent,
    AddressComponent,
    ProviderAttributesComponent,
    PasswordComponent
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
    }),
    AgmSnazzyInfoWindowModule,
    NgxSocialButtonModule
  ],
  providers: [
    GoogleMapsAPIWrapper,
    [
      {provide: HTTP_INTERCEPTORS, useClass: HttpRequestInterceptor, multi: true}
    ],
    {
      provide: SocialServiceConfig,
      useFactory: getAuthServiceConfigs
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
