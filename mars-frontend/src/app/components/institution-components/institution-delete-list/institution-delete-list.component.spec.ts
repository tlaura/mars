import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {InstitutionDeleteListComponent} from './institution-delete-list.component';

describe('InstitutionDeleteListComponent', () => {
  let component: InstitutionDeleteListComponent;
  let fixture: ComponentFixture<InstitutionDeleteListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [InstitutionDeleteListComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InstitutionDeleteListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
