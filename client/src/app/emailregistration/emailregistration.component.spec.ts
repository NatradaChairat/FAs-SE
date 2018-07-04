import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmailregistrationComponent } from './emailregistration.component';

describe('EmailregistrationComponent', () => {
  let component: EmailregistrationComponent;
  let fixture: ComponentFixture<EmailregistrationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmailregistrationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailregistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
