import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventoDetalle } from './evento-detalle';

describe('EventoDetalle', () => {
  let component: EventoDetalle;
  let fixture: ComponentFixture<EventoDetalle>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EventoDetalle]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EventoDetalle);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
