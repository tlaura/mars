import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {InstitutionListComponent} from "./components/institution-list/institution-list.component";
import {InstitutionFormComponent} from "./components/institution-form/institution-form.component";
import {InstitutionDetailsComponent} from "./components/institution-details/institution-details.component";
import {UserFormComponent} from "./components/user-form/user-form.component";
import {RegisterComponent} from "./components/register/register.component";
import {LoginFormComponent} from "./components/login-form/login-form.component";
import {RegistrationCompleteComponent} from "./components/registration-complete/registration-complete.component";
import {RegisterSuccessComponent} from "./components/register-success/register-success.component";


const routes: Routes = [
  {path: "", component: InstitutionListComponent},
  {path: "institution-list", component: InstitutionListComponent},
  {path: "institution-form", component: InstitutionFormComponent},
  {path: "institution-details/:id", component: InstitutionDetailsComponent},
  {path: "user-form", component: UserFormComponent},
  {path: "login", component: LoginFormComponent},
  {path: "register", component: RegisterComponent},
  {path: "registration-complete", component: RegistrationCompleteComponent}

  {path: "login", component: LoginFormComponent},
  {path: "register-success/:token", component: RegisterSuccessComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
