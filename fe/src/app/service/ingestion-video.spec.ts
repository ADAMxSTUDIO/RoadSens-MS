import { TestBed } from '@angular/core/testing';

import { IngestionVideo } from './ingestion-video';

describe('IngestionVideo', () => {
  let service: IngestionVideo;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IngestionVideo);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
