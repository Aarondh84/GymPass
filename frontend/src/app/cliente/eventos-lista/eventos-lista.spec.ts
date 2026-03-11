import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventosLista } from './eventos-lista';

describe('EventosLista', () => {
  let component: EventosLista;
  let fixture: ComponentFixture<EventosLista>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EventosLista]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EventosLista);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
