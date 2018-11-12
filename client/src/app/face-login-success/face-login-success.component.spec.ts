import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FaceLoginSuccessComponent } from './face-login-success.component';

describe('FaceLoginSuccessComponent', () => {
  let component: FaceLoginSuccessComponent;
  let fixture: ComponentFixture<FaceLoginSuccessComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FaceLoginSuccessComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FaceLoginSuccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
