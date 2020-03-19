import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {InstitutionImportCompleteComponent} from './institution-import-complete.component';

describe('InstitutionImportCompleteComponent', () => {
  let component: InstitutionImportCompleteComponent;
  let fixture: ComponentFixture<InstitutionImportCompleteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [InstitutionImportCompleteComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InstitutionImportCompleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
