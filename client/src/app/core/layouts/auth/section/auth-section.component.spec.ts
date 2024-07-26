import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthSectionComponent } from './auth-section.component';

describe('AuthFormComponent', () => {
  let component: AuthSectionComponent;
  let fixture: ComponentFixture<AuthSectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuthSectionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuthSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
