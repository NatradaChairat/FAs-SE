import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmailLoginSuccessComponent } from './email-login-success.component';

describe('EmailLoginSuccessComponent', () => {
  let component: EmailLoginSuccessComponent;
  let fixture: ComponentFixture<EmailLoginSuccessComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmailLoginSuccessComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailLoginSuccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
