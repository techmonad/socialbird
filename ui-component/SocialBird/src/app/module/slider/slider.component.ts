import { Component, AfterContentInit, Input, OnChanges, SimpleChange,  OnInit , ElementRef } from '@angular/core';

declare var jQuery:any;

@Component({
  selector: 'app-slider',
  templateUrl: './slider.component.html',
   styleUrls: ['./slider.component.css']
})
export class SliderComponent implements OnChanges,  AfterContentInit, OnInit  {

    ngOnChanges(changes: {[propKey: string]: SimpleChange}) {
      console.log(this.elements);
    }
    @Input() heading: String;
    @Input() elements: any;
    // = [
    //   {url:'assets/img/modi.jpg', name:'Narendra Modi', handle:'narendramodi', tweet:'Some Text', followers:'270321', rank:'1', rankstatus:'+', following:2323, tweets:15772},
    //   {url:'assets/img/yogi.jpg', name:'Yogi', handle:'myogiadityanath', tweet:'Some Text', followers:'270321', rank:'2', rankstatus:'+', following:2323, tweets:15772},
    //   {url:'assets/img/parrikar.jpeg', name:'Manohar Parrikar', handle:'manoharparrikar ‏', tweet:'Some Text', followers:'270321', rank:'3', rankstatus:'+', following:2323, tweets:15772}
    //   ];

    constructor(private elementRef: ElementRef) { }

    ngAfterContentInit() {
      console.log(this.elements);
    }
    ngOnInit() {
      console.log(this.elements);

    }
}
