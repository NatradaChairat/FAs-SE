import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmailregistrationDiologComponent } from './dialog.component';

describe('EmailregistrationDiologComponent', () => {
  let component: EmailregistrationDiologComponent;
  let fixture: ComponentFixture<EmailregistrationDiologComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmailregistrationDiologComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailregistrationDiologComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
