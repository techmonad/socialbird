import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SliderComponent } from './slider.component';
import { SlickComponent } from './slick/slick.component';

@NgModule({
    declarations: [SliderComponent, SlickComponent],
    imports     : [CommonModule],
    exports     : [SliderComponent, SlickComponent],
})
export class SliderModule {}
