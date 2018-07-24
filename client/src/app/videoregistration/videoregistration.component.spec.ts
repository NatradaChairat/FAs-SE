import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VideoregistrationComponent } from './videoregistration.component';

describe('VideoregistrationComponent', () => {
  let component: VideoregistrationComponent;
  let fixture: ComponentFixture<VideoregistrationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VideoregistrationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VideoregistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
