import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DeletionSuccessComponent} from './deletion-success.component';

describe('DeletionSuccessComponent', () => {
  let component: DeletionSuccessComponent;
  let fixture: ComponentFixture<DeletionSuccessComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DeletionSuccessComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeletionSuccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
