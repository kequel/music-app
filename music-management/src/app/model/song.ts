export class Song {
  id?: string;
  title: string;
  durationMinutes: number;
  albumId?: string;
  albumTitle?: string;

  constructor(title: string = '', durationMinutes: number = 0, albumId?: string) {
    this.title = title;
    this.durationMinutes = durationMinutes;
    this.albumId = albumId;
  }
}