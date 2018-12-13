import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FaceregistrationComponent } from './faceregistration.component';

describe('FaceregistrationComponent', () => {
  let component: FaceregistrationComponent;
  let fixture: ComponentFixture<FaceregistrationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FaceregistrationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FaceregistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
