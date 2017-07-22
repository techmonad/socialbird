import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';


import { SliderModule } from '../../module/slider/slider.module';

import { HomeComponent } from './home.component';
import { HeaderComponent } from './header/header.component';
import { SectionComponent } from './section/section.component';
import { DataService } from '../../provider/data.service';

const routes: Routes = [
  { path: '', component: HomeComponent }
];

@NgModule({
    declarations: [HomeComponent, HeaderComponent, SectionComponent],
    imports     : [CommonModule, RouterModule.forChild(routes), SliderModule],
    exports     : [HomeComponent, HeaderComponent, SectionComponent],
    providers:    [ DataService ]
})
export class HomeModule {}
