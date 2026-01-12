export class Album {
  id?: string;
  title: string;
  artist: string;
  releaseYear: number;

  constructor(title: string = '', artist: string = '', releaseYear: number = new Date().getFullYear()) {
    this.title = title;
    this.artist = artist;
    this.releaseYear = releaseYear;
  }
}