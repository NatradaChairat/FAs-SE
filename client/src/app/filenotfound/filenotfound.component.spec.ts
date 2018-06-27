import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FilenotfoundComponent } from './filenotfound.component';

describe('FilenotfoundComponent', () => {
  let component: FilenotfoundComponent;
  let fixture: ComponentFixture<FilenotfoundComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FilenotfoundComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FilenotfoundComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
