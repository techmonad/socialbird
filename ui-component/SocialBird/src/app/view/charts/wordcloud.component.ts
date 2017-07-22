import { Component, ElementRef, Input, ViewChild, AfterViewInit, Directive, ViewContainerRef, Inject, OnInit, ViewEncapsulation } from "@angular/core";
import * as D3 from 'd3-selection';
import * as d3Scale from "d3-scale";
import * as d3Shape from "d3-shape";
import * as d3Format from "d3-format";
import * as d3Ease from "d3-ease";
import * as cloud from "d3-cloud";

@Component({
  selector: 'wordcloud',
  template: `<div class="word-cloud" #wordcloud></div> `,
  styles: [`
    svg { font: 10px sans-serif; }
    .word-cloud { width: 100%; height:100px;}
  `],
   encapsulation: ViewEncapsulation.None,
})
export class WordcloudChart implements OnInit  {

  private element:any;
  private layout:any;
  private width:number;
  private height:number;
  private margin: any = { top: 0, bottom: 0, left: 0, right: 0};
  @ViewChild('wordcloud') private chartContainer: ElementRef;
  @Input() private words: any= ["Hello", "world", "normally", "you", "want", "more", "words", "than", "this", "you", "want", "more", "words", "than", "this"];

   ngOnInit () {
       console.log('Word Cloud');
       this.buildLayout(this.words);
   }

  private buildLayout(words:any): void {
    this.element = this.chartContainer.nativeElement;
    if (this.element.parentNode !== null) {
      // Get the container dimensions
      const dims = this.element.parentNode.getBoundingClientRect();
      console.log(dims);
      this.width = dims.width;
      this.height = dims.height;
    } else{
      this.width = this.element.offsetWidth - this.margin.left - this.margin.right;
      this.height = this.element.offsetHeight - this.margin.top - this.margin.bottom;
    }
    // this.width = this.element.offsetWidth - this.margin.left - this.margin.right;
    // this.height = this.element.offsetHeight - this.margin.top - this.margin.bottom;
    console.log(this.width);

    this.layout = cloud().size([this.width, this.height])
      .words(words.map(each=>this.buildData(each)))
      .padding(3)
      .rotate(function() { return ~~(Math.random() * 2) * 90; })
      .font("Impact")
      .fontSize(function(d) { return d.size; })
      .on("end", this.drawCloud.bind(this));
    this.layout.start();
  }

  private buildData(d){
    return {text: d, size: 10 + Math.random() * 90, count: 5};
  }

  private drawCloud(words) {



    var fill = d3Scale.scaleOrdinal(d3Scale.schemeCategory20);
    D3.select(this.element).append('svg')
        .attr("width", this.layout.size()[0])
        .attr("height", this.layout.size()[1])
      .append("g")
        .attr("transform", "translate(" + this.layout.size()[0] / 2 + "," + this.layout.size()[1] / 2 + ")")
      .selectAll("text")
        .data(words)
      .enter().append("text")
        .style("font-size", function(d:any) { return d.size + "px"; })
        .style("font-family", "Impact")
        .style("fill", function(d:any, i:any) { return fill(d.height); } )
        .attr("text-anchor", "middle")
        .attr("transform", function(d:any) {
          return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
        })
        .text(function(d:any) { return d.text;})
        .on('click', function (d) { //word Clicked
            console.log(d);
        })
        .on('mouseover', function (d) { //zoom in font-size
            // D3.select(e).transition().style('font-size', function (d) {
            //    return d.size * 1.2 + 'px';
            // }).attr('opacity', 0.5);
            console.log(d);
         })
         .on('mouseout', function (d) {
            // D3.select(e).transition().style('font-size', function (d) {
            //    return d.size + 'px';
            // }).attr('opacity', 1);
         })
        ;
  }

}
