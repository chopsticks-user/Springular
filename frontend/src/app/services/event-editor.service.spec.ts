import { TestBed } from '@angular/core/testing';

import { EventEditorService } from './event-editor.service';

describe('EventEditorService', () => {
  let service: EventEditorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventEditorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
