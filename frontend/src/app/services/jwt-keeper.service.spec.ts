import { TestBed } from '@angular/core/testing';

import { JwtKeeperService } from './jwt-keeper.service';

describe('JwtKeeperService', () => {
  let service: JwtKeeperService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JwtKeeperService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
