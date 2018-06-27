import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterstepOneComponent } from './registerstep-one.component';

describe('RegisterstepOneComponent', () => {
  let component: RegisterstepOneComponent;
  let fixture: ComponentFixture<RegisterstepOneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegisterstepOneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterstepOneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
