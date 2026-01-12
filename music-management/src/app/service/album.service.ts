import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Album } from '../model/album';

@Injectable({
  providedIn: 'root'
})
export class AlbumService {
  private apiUrl = '/api/albums';

  constructor(private http: HttpClient) { }

  findAll(): Observable<Album[]> {
    return this.http.get<Album[]>(this.apiUrl);
  }

  findById(id: string): Observable<Album> {
    return this.http.get<Album>(`${this.apiUrl}/${id}`);
  }

  create(album: Album): Observable<Album> {
    return this.http.post<Album>(this.apiUrl, album);
  }

  update(id: string, album: Album): Observable<Album> {
    return this.http.put<Album>(`${this.apiUrl}/${id}`, album);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}