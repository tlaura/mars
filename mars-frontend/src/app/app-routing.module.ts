import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {InstitutionListComponent} from "./components/institution-components/institution-list/institution-list.component";
import {InstitutionFormComponent} from "./components/institution-components/institution-form/institution-form.component";
import {InstitutionDetailsComponent} from "./components/institution-components/institution-details/institution-details.component";
import {UserFormComponent} from "./components/account-components/user-form/user-form.component";
import {RegisterComponent} from "./components/account-components/registration-components/register/register.component";
import {LoginFormComponent} from "./components/account-components/login-form/login-form.component";
import {RegistrationCompleteComponent} from "./components/account-components/registration-components/registration-complete/registration-complete.component";
import {RegisterSuccessComponent} from "./components/account-components/registration-components/register-success/register-success.component";
import {MyProfileComponent} from "./components/account-components/my-profile/my-profile.component";
import {InstitutionImportComponent} from "./components/institution-components/institution-import/institution-import.component";
import {InstitutionImportCompleteComponent} from "./components/institution-components/institution-import-complete/institution-import-complete.component";
import {HomeComponent} from "./components/home/home.component";
import {NewPasswordComponent} from "./components/account-components/new-password-components/new-password/new-password.component";
import {NewPasswordCompleteComponent} from "./components/account-components/new-password-components/new-password-complete/new-password-complete.component";
import {NewPasswordFormComponent} from "./components/account-components/new-password-components/new-password-form/new-password-form.component";
import {NewPasswordSuccessComponent} from "./components/account-components/new-password-components/new-password-success/new-password-success.component";
import {ProviderDetailsComponent} from "./components/provider-details/provider-details.component";
import {TermsComponent} from "./components/terms/terms.component";
import {PasswordChangeComponent} from "./components/account-components/password-change/password-change.component";


const routes: Routes = [

  {path: "institution-list", component: InstitutionListComponent},
  {path: "provider-details/:id", component: ProviderDetailsComponent},
  {path: "institution-list/:filterType/:filterResult", component: InstitutionListComponent},
  {path: "institution-form", component: InstitutionFormComponent},
  {path: "institution-import", component: InstitutionImportComponent},
  {path: "institution-details/:id", component: InstitutionDetailsComponent},
  {path: "user-form", component: UserFormComponent},
  {path: "login", component: LoginFormComponent},
  {path: "new-password", component: NewPasswordComponent},
  {path: "new-password-complete", component: NewPasswordCompleteComponent},
  {path: "new-password-success", component: NewPasswordSuccessComponent},
  {path: "new-password/:token", component: NewPasswordFormComponent},
  {path: "register", component: RegisterComponent},
  {path: "registration-complete", component: RegistrationCompleteComponent},
  {path: "institution-import-complete", component: InstitutionImportCompleteComponent},
  {path: "register-success/:token", component: RegisterSuccessComponent},
  {path: "my-profile", component: MyProfileComponent},
  {path: "", component: HomeComponent},
  {path: "terms-and-conditions", component: TermsComponent},
  {path: "password-change", component: PasswordChangeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
