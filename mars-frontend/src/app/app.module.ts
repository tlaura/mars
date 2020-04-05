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
import {HomeComponent} from './components/home/home.component';
import {ProviderDetailsComponent} from './components/provider-details/provider-details.component';
import {NewPasswordComponent} from './components/account-components/new-password-components/new-password/new-password.component';
import {NewPasswordCompleteComponent} from './components/account-components/new-password-components/new-password-complete/new-password-complete.component';
import {NewPasswordFormComponent} from './components/account-components/new-password-components/new-password-form/new-password-form.component';
import {NewPasswordSuccessComponent} from './components/account-components/new-password-components/new-password-success/new-password-success.component';
import {TermsComponent} from './components/terms/terms.component';
import {PasswordChangeComponent} from './components/account-components/new-password-components/password-change/password-change.component';
import {PasswordChangeSuccessComponent} from './components/account-components/new-password-components/password-change-success/password-change-success.component';
import {EvaluateListComponent} from './components/institution-components/evaluate-list/evaluate-list.component';
import {JwtInterceptor} from "./utils/auth/jwt.interceptor";
import {ErrorInterceptor} from "./utils/auth/error.interceptor";
import {JwtHelperService, JwtModule} from "@auth0/angular-jwt";
import {NormalUserComponent} from "./components/account-components/registration-components/register/normal-user/normal-user.component";
import {ProviderUserComponent} from "./components/account-components/registration-components/register/provider-user/provider-user.component";
import {UserAttributesComponent} from "./components/form-elements-components/user-attributes/user-attributes.component";
import {ConfirmDeletionComponent} from './components/account-components/confirm-deletion/confirm-deletion.component';
import {DeletionSuccessComponent} from './components/account-components/deletion-success/deletion-success.component';
import {ChatComponent} from './components/chat/chat.component';
import {MessageWindowComponent} from './components/chat/message-window/message-window.component';
import {InstitutionDeleteListComponent} from './components/institution-components/institution-delete-list/institution-delete-list.component';

export function getAuthServiceConfigs() {
  let config = new SocialServiceConfig()
    .addFacebook("229963898396055");
  // .addGoogle("Your-Google-Client-Id")
  //  .addLinkedIn("Your-LinkedIn-Client-Id");
  return config;
}

export function getToken(): string {
  return localStorage.getItem('token');
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
    HomeComponent,
    ProviderDetailsComponent,
    NewPasswordComponent,
    NewPasswordCompleteComponent,
    NewPasswordFormComponent,
    NewPasswordSuccessComponent,
    TermsComponent,
    NormalUserComponent,
    ProviderUserComponent,
    PasswordChangeComponent,
    PasswordChangeSuccessComponent,
    EvaluateListComponent,
    UserAttributesComponent,
    ConfirmDeletionComponent,
    DeletionSuccessComponent,
    ChatComponent,
    MessageWindowComponent,
    InstitutionDeleteListComponent,
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
    NgxSocialButtonModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: getToken
      }
    })
  ],
  providers: [
    GoogleMapsAPIWrapper,
    [
      {provide: HTTP_INTERCEPTORS, useClass: HttpRequestInterceptor, multi: true},
      {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
      {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true}
    ],
    {
      provide: SocialServiceConfig,
      useFactory: getAuthServiceConfigs
    },
    JwtHelperService
  ],
  bootstrap: [AppComponent]
})

export class AppModule {
}
