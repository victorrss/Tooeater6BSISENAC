import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TooeatsComponent } from './tooeats.component';

describe('TooeatsComponent', () => {
  let component: TooeatsComponent;
  let fixture: ComponentFixture<TooeatsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TooeatsComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TooeatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
