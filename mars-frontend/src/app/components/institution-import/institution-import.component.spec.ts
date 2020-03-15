import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {InstitutionImportComponent} from './institution-import.component';

describe('InstitutionImportComponent', () => {
  let component: InstitutionImportComponent;
  let fixture: ComponentFixture<InstitutionImportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [InstitutionImportComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InstitutionImportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
