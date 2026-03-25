import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TiposEvento } from './tipos-evento';

describe('TiposEvento', () => {
  let component: TiposEvento;
  let fixture: ComponentFixture<TiposEvento>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TiposEvento]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TiposEvento);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
