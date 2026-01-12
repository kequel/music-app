import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AlbumListComponent } from './component/album-list/album-list.component';
import { AlbumAddComponent } from './component/album-add/album-add.component';
import { AlbumEditComponent } from './component/album-edit/album-edit.component';
import { AlbumDetailsComponent } from './component/album-details/album-details.component';
import { SongAddComponent } from './component/song-add/song-add.component';
import { SongEditComponent } from './component/song-edit/song-edit.component';
import { SongDetailsComponent } from './component/song-details/song-details.component';

const routes: Routes = [
  { path: '', redirectTo: '/albums', pathMatch: 'full' },
  { path: 'albums', component: AlbumListComponent },
  { path: 'albums/add', component: AlbumAddComponent },
  { path: 'albums/:albumId', component: AlbumDetailsComponent },
  { path: 'albums/:albumId/edit', component: AlbumEditComponent },
  { path: 'albums/:albumId/songs/add', component: SongAddComponent },
  { path: 'albums/:albumId/songs/:songId', component: SongDetailsComponent },
  { path: 'albums/:albumId/songs/:songId/edit', component: SongEditComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }