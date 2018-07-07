import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmailRegistrationComponent } from './emailregistration.component';

describe('EmailregistrationComponent', () => {
  let component: EmailRegistrationComponent;
  let fixture: ComponentFixture<EmailRegistrationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmailRegistrationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
