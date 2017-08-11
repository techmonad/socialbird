import { Component, AfterContentInit, ElementRef } from '@angular/core';

declare var jQuery:any;
@Component({
  selector: 'slick-slider',
  template: `<ng-content></ng-content>`
})
export class SlickComponent implements AfterContentInit  {

    config: any = {
      // arrows: true,
      dots: false,
      infinite: true,
      //speed: 300,
      slidesToShow: 4,
      slidesToScroll: 4,
      //adaptiveHeight: true,
      responsive: [
        {
          breakpoint: 1024,
          settings: {
            infinite: true,
            slidesToShow: 4,
            slidesToScroll: 1,
            swipeToSlide: true,
            draggable: true,
            centerPadding: '10px',
          }
        },
        {
          breakpoint: 700,
          settings: {
            slidesToShow: 3,
            slidesToScroll: 3
          }
        },
        {
          breakpoint: 480,
          settings: {
            slidesToShow: 1,
            slidesToScroll: 1
          }
    }
    // You can unslick at a given breakpoint now by adding:
    // settings: "unslick"
    // instead of a settings object
  ]
    };
    public person : any[];
    public slides: any = [];
    public $element : any;
    private initialized: Boolean = false;
    constructor(private elementRef: ElementRef) { }

    ngAfterContentInit() {
      this.$element  = jQuery(this.elementRef.nativeElement).slick(this.config);
  //     slideIndex++;
  // $('.add-remove').slick('slickAdd','<div><h3>' + slideIndex + '</h3></div>');
    }
}
