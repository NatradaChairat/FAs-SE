import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PhonenoVerificationComponent } from './phoneno-verification.component';

describe('PhonenoVerificationComponent', () => {
  let component: PhonenoVerificationComponent;
  let fixture: ComponentFixture<PhonenoVerificationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PhonenoVerificationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PhonenoVerificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
