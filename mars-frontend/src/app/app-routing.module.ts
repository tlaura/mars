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
import {MyProfileComponent} from "./components/my-profile/my-profile.component";
import {InstitutionImportComponent} from "./components/institution-import/institution-import.component";
import {InstitutionImportCompleteComponent} from "./components/institution-import-complete/institution-import-complete.component";
import {EditProfileComponent} from "./components/edit-profile/edit-profile.component";


const routes: Routes = [

  {path: "institution-list", component: InstitutionListComponent},
  {path: "institution-list/:filterType/:filterResult", component: InstitutionListComponent},
  {path: "institution-form", component: InstitutionFormComponent},
  {path: "institution-import", component: InstitutionImportComponent},
  {path: "institution-details/:id", component: InstitutionDetailsComponent},
  {path: "user-form", component: UserFormComponent},
  {path: "login", component: LoginFormComponent},
  {path: "register", component: RegisterComponent},
  {path: "registration-complete", component: RegistrationCompleteComponent},
  {path: "institution-import-complete", component: InstitutionImportCompleteComponent},
  {path: "register-success/:token", component: RegisterSuccessComponent},
  {path: "my-profile", component: MyProfileComponent},
  {path: "", component: InstitutionListComponent},
  {path: "my-profile/edit-profile", component: EditProfileComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
