import { TestBed } from '@angular/core/testing';

import { ApiRouteService } from './api-route.service';

describe('ApiRouteService', () => {
  let service: ApiRouteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApiRouteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
