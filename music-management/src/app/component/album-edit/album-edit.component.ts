import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Album } from '../../model/album';
import { AlbumService } from '../../service/album.service';

@Component({
  selector: 'app-album-edit',
  templateUrl: './album-edit.component.html',
  styleUrls: ['./album-edit.component.css']
})
export class AlbumEditComponent implements OnInit {
  album: Album = new Album();
  albumId: string = '';

  constructor(
    private albumService: AlbumService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.albumId = this.route.snapshot.paramMap.get('albumId') || '';
    
    if (this.albumId) {
      this.albumService.findById(this.albumId).subscribe({
        next: (data) => {
          this.album = data;
        },
        error: (err) => {
          console.error('Error loading album:', err);
          alert('Album not found');
          this.router.navigate(['/albums']);
        }
      });
    }
  }

  onSubmit(): void {
    this.albumService.update(this.albumId, this.album).subscribe({
      next: () => {
        this.router.navigate(['/albums', this.albumId]);
      },
      error: (err) => {
        console.error('Error updating album:', err);
        alert('Error updating album. Please try again.');
      }
    });
  }

  onCancel(): void {
    this.router.navigate(['/albums', this.albumId]);
  }
}