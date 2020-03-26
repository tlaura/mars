import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {InstitutionFormComponent} from './components/institution-components/institution-form/institution-form.component';
import {InstitutionListComponent} from './components/institution-components/institution-list/institution-list.component';
import {InstitutionDetailsComponent} from './components/institution-components/institution-details/institution-details.component';
import {UserFormComponent} from './components/account-components/user-form/user-form.component';
import {NavbarComponent} from "./components/navbar/navbar.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {RegisterComponent} from './components/account-components/registration-components/register/register.component';
import {Ng2SearchPipeModule} from "ng2-search-filter";
import {NgxPaginationModule} from "ngx-pagination";
import {AgmCoreModule, GoogleMapsAPIWrapper} from "@agm/core";
import {MapComponent} from './components/map/map.component';
import {LoginFormComponent} from "./components/account-components/login-form/login-form.component";
import {HttpRequestInterceptor} from "./utils/httpRequestInterceptor";
import {RegistrationCompleteComponent} from './components/account-components/registration-components/registration-complete/registration-complete.component';
import {RegisterSuccessComponent} from './components/account-components/registration-components/register-success/register-success.component';
import {MyProfileComponent} from './components/account-components/my-profile/my-profile.component';
import {InstitutionImportComponent} from './components/institution-components/institution-import/institution-import.component';
import {SendMailComponent} from './components/send-mail/send-mail.component';
import {AgmSnazzyInfoWindowModule} from "@agm/snazzy-info-window";
import {NgxSocialButtonModule, SocialServiceConfig} from "ngx-social-button";
import {EmailComponent} from './components/form-elements-components/email/email.component';
import {ContactsComponent} from './components/form-elements-components/contacts/contacts.component';
import {AddressComponent} from './components/form-elements-components/address/address.component';
import {ProviderAttributesComponent} from './components/form-elements-components/provider-attributes/provider-attributes.component';
import {PasswordComponent} from './components/form-elements-components/password/password.component';
import {InstitutionComponent} from './components/form-elements-components/institution/institution.component';
import {OpeningHoursComponent} from './components/form-elements-components/opening-hours/opening-hours.component';
import {environment} from "../environments/environment";
import {InstitutionImportCompleteComponent} from './components/institution-components/institution-import-complete/institution-import-complete.component';
import {EditProfileComponent} from './components/account-components/edit-profile/edit-profile.component';
import {HomeComponent} from './components/home/home.component';
import {ProviderDetailsComponent} from './components/provider-details/provider-details.component';
import {NewPasswordComponent} from './components/account-components/new-password-components/new-password/new-password.component';
import {NewPasswordCompleteComponent} from './components/account-components/new-password-components/new-password-complete/new-password-complete.component';
import {NewPasswordFormComponent} from './components/account-components/new-password-components/new-password-form/new-password-form.component';
import {NewPasswordSuccessComponent} from './components/account-components/new-password-components/new-password-success/new-password-success.component';
import {TermsComponent} from './components/terms/terms.component';

export function getAuthServiceConfigs() {
  let config = new SocialServiceConfig()
    .addFacebook("229963898396055");
  // .addGoogle("Your-Google-Client-Id")
  //  .addLinkedIn("Your-LinkedIn-Client-Id");
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
    PasswordComponent,
    InstitutionComponent,
    OpeningHoursComponent,
    InstitutionImportCompleteComponent,
    EditProfileComponent,
    HomeComponent,
    ProviderDetailsComponent,
    NewPasswordComponent,
    NewPasswordCompleteComponent,
    NewPasswordFormComponent,
    NewPasswordSuccessComponent,
    TermsComponent,
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
