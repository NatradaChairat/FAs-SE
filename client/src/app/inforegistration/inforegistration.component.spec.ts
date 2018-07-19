import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InforegistrationComponent } from './inforegistration.component';

describe('InforegistrationComponent', () => {
  let component: InforegistrationComponent;
  let fixture: ComponentFixture<InforegistrationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InforegistrationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InforegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
