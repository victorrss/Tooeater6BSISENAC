import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TooeatComponent } from './tooeat.component';

describe('TooeatComponent', () => {
  let component: TooeatComponent;
  let fixture: ComponentFixture<TooeatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TooeatComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TooeatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
