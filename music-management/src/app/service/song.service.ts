import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Song } from '../model/song';

@Injectable({
  providedIn: 'root'
})
export class SongService {
  constructor(private http: HttpClient) { }

  findAll(): Observable<Song[]> {
    return this.http.get<Song[]>('/api/songs');
  }

  findByAlbumId(albumId: string): Observable<Song[]> {
    return this.http.get<Song[]>(`/api/albums/${albumId}/songs`);
  }

  findById(albumId: string, songId: string): Observable<Song> {
    return this.http.get<Song>(`/api/albums/${albumId}/songs/${songId}`);
  }

  create(albumId: string, song: Song): Observable<Song> {
    return this.http.post<Song>(`/api/albums/${albumId}/songs`, song);
  }

  update(albumId: string, songId: string, song: Song): Observable<Song> {
    return this.http.put<Song>(`/api/albums/${albumId}/songs/${songId}`, song);
  }

  delete(albumId: string, songId: string): Observable<void> {
    return this.http.delete<void>(`/api/albums/${albumId}/songs/${songId}`);
  }
}
