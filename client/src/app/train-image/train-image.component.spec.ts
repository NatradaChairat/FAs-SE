import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrainImageComponent } from './train-image.component';

describe('TrainImageComponent', () => {
  let component: TrainImageComponent;
  let fixture: ComponentFixture<TrainImageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrainImageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrainImageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
