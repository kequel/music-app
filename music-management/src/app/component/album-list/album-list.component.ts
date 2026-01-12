import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Album } from '../../model/album';
import { AlbumService } from '../../service/album.service';

@Component({
  selector: 'app-album-list',
  templateUrl: './album-list.component.html',
  styleUrls: ['./album-list.component.css']
})
export class AlbumListComponent implements OnInit {
  albums: Album[] = [];

  constructor(
    private albumService: AlbumService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadAlbums();
  }

  loadAlbums(): void {
    this.albumService.findAll().subscribe({
      next: (data) => {
        this.albums = data;
      },
      error: (err) => {
        console.error('Error loading albums:', err);
      }
    });
  }

  deleteAlbum(id: string | undefined): void {
    if (!id) return;
    
    if (confirm('Are you sure you want to delete this album?')) {
      this.albumService.delete(id).subscribe({
        next: () => {
          this.loadAlbums();
        },
        error: (err) => {
          console.error('Error deleting album:', err);
        }
      });
    }
  }

  goToDetails(id: string | undefined): void {
    if (id) {
      this.router.navigate(['/albums', id]);
    }
  }

  goToEdit(id: string | undefined): void {
    if (id) {
      this.router.navigate(['/albums', id, 'edit']);
    }
  }

  goToAdd(): void {
    this.router.navigate(['/albums/add']);
  }
}