import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Song } from '../../model/song';
import { SongService } from '../../service/song.service';

@Component({
  selector: 'app-song-add',
  templateUrl: './song-add.component.html',
  styleUrls: ['./song-add.component.css']
})
export class SongAddComponent implements OnInit {
  song: Song = new Song();
  albumId: string = '';

  constructor(
    private songService: SongService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.albumId = this.route.snapshot.paramMap.get('albumId') || '';
    this.song.albumId = this.albumId;
  }

onSubmit(): void {
  this.songService.create(this.albumId, this.song).subscribe({
    next: () => {
      this.router.navigate(['/albums', this.albumId]);
    },
    error: (err) => {
      console.error('Error creating song:', err);
      alert('Error creating song. Please try again.');
    }
  });
}

  onCancel(): void {
    this.router.navigate(['/albums', this.albumId]);
  }
}