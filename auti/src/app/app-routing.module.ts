import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {InstitutionListComponent} from "./components/institution-list/institution-list.component";
import {InstitutionFormComponent} from "./components/institution-form/institution-form.component";
import {InstitutionDetailsComponent} from "./components/institution-details/institution-details.component";
import {UserFormComponent} from "./components/user-form/user-form.component";


const routes: Routes = [
  {path: "", component: InstitutionListComponent},
  {path: "institution-list", component: InstitutionListComponent},
  {path: "institution-form", component: InstitutionFormComponent},
  {path: "institution-details/:id", component: InstitutionDetailsComponent},
  {path: "user-form", component: UserFormComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
