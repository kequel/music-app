import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Album } from '../../model/album';
import { AlbumService } from '../../service/album.service';

@Component({
  selector: 'app-album-add',
  templateUrl: './album-add.component.html',
  styleUrls: ['./album-add.component.css']
})
export class AlbumAddComponent {
  album: Album = new Album();

  constructor(
    private albumService: AlbumService,
    private router: Router
  ) { }

  onSubmit(): void {
    this.albumService.create(this.album).subscribe({
      next: () => {
        this.router.navigate(['/albums']);
      },
      error: (err) => {
        console.error('Error creating album:', err);
        alert('Error creating album. Please try again.');
      }
    });
  }

  onCancel(): void {
    this.router.navigate(['/albums']);
  }
}