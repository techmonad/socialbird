import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule, Http } from '@angular/http';
import { TranslateService } from '@ngx-translate/core';

import { INIT_INITIALIZER, ConfigService } from './provider/init.service';

import { AppComponent } from './app.component';
import { AppTranslationModule } from './app.translation.module';
import { HomeModule } from './view/home/home.module';
import { DetailModule } from './view/detail/detail.module';
/* Routing Module */
import { AppRoutingModule } from './app.routes';
import { DataService } from './provider/data.service';
//import { ChartsModule } from 'ng2-charts';
import { WordcloudChart } from './view/charts/wordcloud.component';
@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    AppTranslationModule,
    AppRoutingModule,
    HomeModule,
    DetailModule,
    //ChartsModule
    //ChartsModule
    // Specify your library as an import
  ],
  providers: [
    ConfigService,
    DataService,
    INIT_INITIALIZER
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  bootstrap: [AppComponent]
})
export class AppModule { }
