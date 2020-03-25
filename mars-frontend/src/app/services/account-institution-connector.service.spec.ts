import {TestBed} from '@angular/core/testing';

import {AccountInstitutionConnectorService} from './account-institution-connector.service';

describe('AccountInstitutionConnectorService', () => {
  let service: AccountInstitutionConnectorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AccountInstitutionConnectorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
