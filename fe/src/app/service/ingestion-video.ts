import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class IngestionVideo {
 http = inject(HttpClient);
 
 register(reporter: Record<string, any>) {
  return this.http.post('http://localhost/ingestion/auth/login', reporter);
 }
}
