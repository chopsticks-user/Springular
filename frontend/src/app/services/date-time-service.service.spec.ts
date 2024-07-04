import { TestBed } from '@angular/core/testing';

import { DateTimeServiceService } from './date-time-service.service';

describe('DateTimeServiceService', () => {
  let service: DateTimeServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DateTimeServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
