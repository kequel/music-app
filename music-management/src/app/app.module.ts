import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AlbumListComponent } from './component/album-list/album-list.component';
import { AlbumAddComponent } from './component/album-add/album-add.component';
import { AlbumEditComponent } from './component/album-edit/album-edit.component';
import { AlbumDetailsComponent } from './component/album-details/album-details.component';
import { SongAddComponent } from './component/song-add/song-add.component';
import { SongEditComponent } from './component/song-edit/song-edit.component';
import { SongDetailsComponent } from './component/song-details/song-details.component';

@NgModule({
  declarations: [
    AppComponent,
    AlbumListComponent,
    AlbumAddComponent,
    AlbumEditComponent,
    AlbumDetailsComponent,
    SongAddComponent,
    SongEditComponent,
    SongDetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }