import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Song } from '../../model/song';
import { SongService } from '../../service/song.service';

@Component({
  selector: 'app-song-details',
  templateUrl: './song-details.component.html',
  styleUrls: ['./song-details.component.css']
})
export class SongDetailsComponent implements OnInit {
  song: Song | null = null;
  albumId: string = '';
  songId: string = '';

  constructor(
    private songService: SongService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

ngOnInit(): void {
  this.albumId = this.route.snapshot.paramMap.get('albumId') || '';
  this.songId = this.route.snapshot.paramMap.get('songId') || '';
  
  if (this.songId && this.albumId) {
    this.songService.findById(this.albumId, this.songId).subscribe({
      next: (data) => {
        this.song = data;
      },
      error: (err) => {
        console.error('Error loading song:', err);
      }
    });
  }
}

  goBack(): void {
    this.router.navigate(['/albums', this.albumId]);
  }

  goToEdit(): void {
    this.router.navigate(['/albums', this.albumId, 'songs', this.songId, 'edit']);
  }

deleteSong(): void {
  if (confirm('Are you sure you want to delete this song?')) {
    this.songService.delete(this.albumId, this.songId).subscribe({
      next: () => {
        this.router.navigate(['/albums', this.albumId]);
      },
      error: (err) => {
        console.error('Error deleting song:', err);
        alert('Error deleting song. Please try again.');
      }
    });
  }
}
}