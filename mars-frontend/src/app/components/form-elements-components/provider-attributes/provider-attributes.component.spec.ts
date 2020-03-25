import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ProviderAttributesComponent} from './provider-attributes.component';

describe('ProviderAttributesComponent', () => {
  let component: ProviderAttributesComponent;
  let fixture: ComponentFixture<ProviderAttributesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProviderAttributesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProviderAttributesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
