import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Album } from '../../model/album';
import { Song } from '../../model/song';
import { AlbumService } from '../../service/album.service';
import { SongService } from '../../service/song.service';

@Component({
  selector: 'app-album-details',
  templateUrl: './album-details.component.html',
  styleUrls: ['./album-details.component.css']
})
export class AlbumDetailsComponent implements OnInit {
  album: Album | null = null;
  songs: Song[] = [];
  albumId: string = '';

  constructor(
    private albumService: AlbumService,
    private songService: SongService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.albumId = this.route.snapshot.paramMap.get('albumId') || '';
    
    if (this.albumId) {
      this.loadAlbum();
      this.loadSongs();
    }
  }

  loadAlbum(): void {
    this.albumService.findById(this.albumId).subscribe({
      next: (data) => {
        this.album = data;
      },
      error: (err) => {
        console.error('Error loading album:', err);
      }
    });
  }

loadSongs(): void {
  console.log('=== Loading songs for album:', this.albumId);
  this.songService.findByAlbumId(this.albumId).subscribe({
    next: (data) => {
      console.log('=== Songs received:', data);
      console.log('=== Number of songs:', data.length);
      this.songs = data;
    },
    error: (err) => {
      console.error('=== Error loading songs:', err);
    }
  });
}

deleteSong(songId: string | undefined): void {
  if (!songId) return;
  
  if (confirm('Are you sure you want to delete this song?')) {
    this.songService.delete(this.albumId, songId).subscribe({
      next: () => {
        this.loadSongs();
      },
      error: (err) => {
        console.error('Error deleting song:', err);
      }
    });
  }
}

  goToSongDetails(songId: string | undefined): void {
    if (songId) {
      this.router.navigate(['/albums', this.albumId, 'songs', songId]);
    }
  }

  goToSongEdit(songId: string | undefined): void {
    if (songId) {
      this.router.navigate(['/albums', this.albumId, 'songs', songId, 'edit']);
    }
  }

  goToAddSong(): void {
    this.router.navigate(['/albums', this.albumId, 'songs', 'add']);
  }

  goBack(): void {
    this.router.navigate(['/albums']);
  }

  goToEdit(): void {
    this.router.navigate(['/albums', this.albumId, 'edit']);
  }
}