import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {NewPasswordSuccessComponent} from './new-password-success.component';

describe('NewPasswordSuccessComponent', () => {
  let component: NewPasswordSuccessComponent;
  let fixture: ComponentFixture<NewPasswordSuccessComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [NewPasswordSuccessComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewPasswordSuccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
