import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountDetailDialogComponent } from './account-detail-dialog.component';

describe('AccountDetailDialogComponent', () => {
  let component: AccountDetailDialogComponent;
  let fixture: ComponentFixture<AccountDetailDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccountDetailDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountDetailDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
