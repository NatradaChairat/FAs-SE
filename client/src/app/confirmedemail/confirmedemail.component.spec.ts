import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmedemailComponent } from './confirmedemail.component';

describe('ConfirmedemailComponent', () => {
  let component: ConfirmedemailComponent;
  let fixture: ComponentFixture<ConfirmedemailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfirmedemailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmedemailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
