import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { ChartsModule } from 'ng2-charts';

import { DetailComponent } from './detail.component';
import { DataService } from '../../provider/data.service';

import { GaugeChart } from '../charts/gauge.component';
import { FollowersChart } from '../charts/follower.component';
import { TweetsChart } from '../charts/tweets.component';
import { SentimentalChart } from '../charts/sentimental.component';

import { HalfDoughnutChart } from '../charts/halfdoughnut.component';
import { WordcloudChart } from '../charts/wordcloud.component';

const routes: Routes = [
  { path: '', component: DetailComponent }
];

@NgModule({
    declarations: [DetailComponent, GaugeChart, FollowersChart, TweetsChart, SentimentalChart, HalfDoughnutChart,WordcloudChart],
    imports     : [CommonModule, ChartsModule, RouterModule.forChild(routes)],
    exports     : [DetailComponent],
    providers:    [ DataService, GaugeChart, FollowersChart, TweetsChart, SentimentalChart, HalfDoughnutChart,WordcloudChart],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class DetailModule {}
