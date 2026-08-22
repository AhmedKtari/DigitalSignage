import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagingSignsComponent } from './managing-signs';

describe('ManagingSignsComponent', () => {
  let component: ManagingSignsComponent;
  let fixture: ComponentFixture<ManagingSignsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManagingSignsComponent],
    }).compileComponents();
                                                       
    fixture = TestBed.createComponent(ManagingSignsComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
