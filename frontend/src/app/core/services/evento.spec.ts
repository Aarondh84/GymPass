import { TestBed } from '@angular/core/testing';

import { Evento } from './evento';

describe('Evento', () => {
  let service: Evento;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Evento);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
