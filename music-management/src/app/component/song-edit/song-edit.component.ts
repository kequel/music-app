import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Song } from '../../model/song';
import { SongService } from '../../service/song.service';

@Component({
  selector: 'app-song-edit',
  templateUrl: './song-edit.component.html',
  styleUrls: ['./song-edit.component.css']
})
export class SongEditComponent implements OnInit {
  song: Song = new Song();
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
        alert('Song not found');
        this.router.navigate(['/albums', this.albumId]);
      }
    });
  }
}

onSubmit(): void {
  this.songService.update(this.albumId, this.songId, this.song).subscribe({
    next: () => {
      this.router.navigate(['/albums', this.albumId, 'songs', this.songId]);
    },
    error: (err) => {
      console.error('Error updating song:', err);
      alert('Error updating song. Please try again.');
    }
  });
}

  onCancel(): void {
    this.router.navigate(['/albums', this.albumId, 'songs', this.songId]);
  }
}