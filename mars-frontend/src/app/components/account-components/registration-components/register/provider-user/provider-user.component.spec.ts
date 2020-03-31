import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ProviderUserComponent} from './provider-user.component';

describe('ProviderUserComponent', () => {
  let component: ProviderUserComponent;
  let fixture: ComponentFixture<ProviderUserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ProviderUserComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProviderUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
