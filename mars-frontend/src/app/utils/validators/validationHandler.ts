import {HttpErrorResponse} from '@angular/common/http';
import {FormGroup} from '@angular/forms';

export function validationHandler(error, form: FormGroup) {
  if (error instanceof HttpErrorResponse && error.status === 400) {
    for (const errorFromServer of error.error.fieldErrors) {
      const formControl = form.get(errorFromServer.field);
      if (formControl) {
        formControl.setErrors({serverError: errorFromServer.message});
      }
    }
  }
  if (error instanceof HttpErrorResponse && error.status === 404) {
    const formControl = form.get('address');
    if (formControl) {
      formControl.setErrors({serverError: 'Hibásan megadott cím!'});
    }
  }
}
