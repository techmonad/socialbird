import { Component, ElementRef, Input, ViewChild, AfterViewInit, Directive, ViewContainerRef, Inject, OnInit } from "@angular/core";
import * as D3 from 'd3-selection';
import * as d3Scale from "d3-scale";
import * as d3Shape from "d3-shape";
import * as d3Format from "d3-format";
import * as d3Ease from "d3-ease";
import * as d3Interpolate from 'd3-interpolate';
import * as d3Transition from 'd3-transition';

@Component({
  selector: 'gaugegraph',
  template: `<div class="chart-gauge" #gaugeChart></div> `, // <svg width="450" height="300" #newgaugeGraph></svg>
  styles: [`
    .chart-gauge { width: 100%; height:150px; right: -40px;}
    .chart-filled { color: steelblue; }
    .chart-empty { fill: #dedede; }
    .chart-target { fill: #ff0000; }
    .needle, .needle-center { fill: #464A4F; }
    svg { font: 10px sans-serif; }
  `]
})
export class GaugeChart implements OnInit  {
//http://sharepointpals.com/post/How-To-Enable-Target-Value-and-Actual-Value-In-D3-Gauge-Chart
//https://jsfiddle.net/v1tok1k6/1/
  private svg: any;
  private margin: any = { top: 10, bottom: 10, left: 10, right: 10};
  private radius: number;
  private xScale: number;
  private yScale: number;
  private xAxis: number;
  private yAxis: number;
  private arc1: any;
  private arc2: any;
  private chartInset: any;
  private barWidth: number;
  private totalPercent: number;
  private width: number;
  private height: number;
  private targetText:string;
  private actualText:string;
  private formatValue:any;
  private len:number=0;
  private element:any;//HTMLElement;
  private chart: any;
  @Input() percent:number = .3;
  @ViewChild('gaugeChart') private chartContainer: ElementRef;

  private gaugeBar: any = {
    chartFilled: {
      fill: 'steelblue'
    },
    chartEmpty: {
      fill: '#dedede'
    },
    needle: {
      fill: '#464A4F'
    }
  };

   ngOnInit () {
       console.log('Gaugegraph Init');
       this.setup();
       this.buildSVG();
       this.buildGauge();
   }
    private setup(): void {
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
      this.width = this.element.offsetWidth - this.margin.left - this.margin.right;
      this.height = this.element.offsetHeight - this.margin.top - this.margin.bottom;

      this.radius = Math.min(this.width, this.height) / 2;
      this.barWidth = 40 * this.width / 300; //this.height
      this.chartInset = 10;
      // Orientation of gauge:
      this.totalPercent = .75;
    }
    private buildSVG(): void {
      // Create SVG element
      this.svg = D3.select(this.element).append('svg')
      .attr('width', this.width)
      .attr('height', this.height);

      // Add layer for the panel
      this.chart = this.svg.append('g').attr(`transform`, `translate( ${((this.width + this.margin.left) / 2)}, ${((this.height + this.margin.top) / 2)} )`);
      this.chart.append('path').attr('class', "chart-filled").attr('fill', this.gaugeBar.chartFilled.fill);
      this.chart.append('path').attr('class', "arc chart-empty").attr('fill', this.gaugeBar.chartEmpty.fill);

      // svg.arc to arc
      this.arc2 = d3Shape.arc().outerRadius(this.radius - this.chartInset).innerRadius(this.radius - this.chartInset - this.barWidth);
      this.arc1 = d3Shape.arc().outerRadius(this.radius - this.chartInset).innerRadius(this.radius - this.chartInset - this.barWidth);

    //   this.targetText = this.chart.append("text")
    //                  .attr('id', "Value")
    //                  .attr("font-size", 16)
    //                  .attr("text-anchor", "middle")
    //                  .attr("dy", ".5em")
    //                  .style("fill", '#0000FF');
    //  this.actualText = this.chart.append('text')
    //              .attr('id', "Value")
    //                  .attr("font-size", 16)
    //                  .attr("text-anchor", "middle")
    //                  .attr("dy", ".5em")
    //                  .style("fill", '#0000FF')
    //                  .attr('class', 'needle').attr('cx', 0).attr('cy', 0).attr('r', this.radius);
    //  this.formatValue = d3Format.format('1%');
    }

    buildGauge(){
      this.needle();
      this.moveTo(this.percent);
    }

    repaintGauge (actualPerc, targetPerc) {
      var padRad = 0.025;
      var next_start = this.totalPercent;
			var arcStartRad = this.percToRad(next_start);
			var arcEndRad = arcStartRad + this.percToRad(actualPerc / 2);
			next_start += actualPerc / 2;
			this.arc1.startAngle(arcStartRad).endAngle(arcEndRad);

			arcStartRad = this.percToRad(next_start);
			arcEndRad = arcStartRad + this.percToRad((1 - actualPerc) / 2);
			this.arc2.startAngle(arcStartRad + padRad).endAngle(arcEndRad);

			this.chart.select(".chart-filled").attr('d', this.arc1);
			this.chart.select(".chart-empty").attr('d', this.arc2);
		}

    private needle():any {
      this.len = this.width / 3;
		  this.radius = this.len / 6;
      this.chart.append('circle')
      .attr('class', 'needle-center').attr('cx', 0).attr('cy', 0).attr('r', this.radius).attr('fill', this.gaugeBar.needle.fill);
      return this.chart.append('path').attr('d', this.recalcPointerPos.call(this, 0))
      .attr('class', 'needle')
      .attr('fill', this.gaugeBar.needle.fill);
		}

		private moveTo = function(perc, perc2?:any) {
		  var self:any;
			var oldValue = perc || 0;
		  self = this;

      //console.log(this.chart.)
      // Reset pointer position
      d3Transition.transition(this.chart).
      transition().delay(100).duration(200).select('.needle').tween('reset-progress', function() {
      var needle = D3.select(this);
      return function(percentOfPercent) {
        var progress = (1 - percentOfPercent) * oldValue;

        self.repaintGauge(progress);
       return needle.attr('d', self.recalcPointerPos.call(self, progress));
      };
      });

      d3Transition.transition(this.chart).
      transition().delay(300).duration(1500).select('.needle').tween('progress', function(d, i, e) {
        var needle = D3.select(this);
        return function(percentOfPercent) {
          var progress = percentOfPercent * perc;

          // var thetaRad = self.percToRad(perc2 / 2);
          // var textX = -(self.len + 5) * Math.cos(self.thetaRad);
          // var textY = -(self.len + 5) * Math.sin(self.thetaRad);
          //
          // self.actualText.text('hasdhiad');
          // self.targetText.text('asdasd').attr('transform', "translate(" + textX + "," + textY + ")");

          self.repaintGauge(progress);
          return needle.attr('d', self.recalcPointerPos.call(self, progress));
        };
      });
		}


    percToDeg(perc) { return perc * 360; };

    percToRad(perc) { return this.degToRad(this.percToDeg(perc)); };

    degToRad(deg) {  return deg * Math.PI / 180; };

    /** * Helper function that returns the `d` value for moving the needle **/
    private thetaRad;
    recalcPointerPos(perc):string {
      this.thetaRad = this.percToRad(perc / 2);
      var centerX = 0;
      var centerY = 0;
      var topX = centerX - this.len * Math.cos(this.thetaRad);
      var topY = centerY - this.len * Math.sin(this.thetaRad);
      var leftX = centerX - this.radius * Math.cos(this.thetaRad - Math.PI / 2);
      var leftY = centerY - this.radius * Math.sin(this.thetaRad - Math.PI / 2);
      var rightX = centerX - this.radius * Math.cos(this.thetaRad + Math.PI / 2);
      var rightY = centerY - this.radius * Math.sin(this.thetaRad + Math.PI / 2);
      return "M " + leftX + " " + leftY + " L " + topX + " " + topY + " L " + rightX + " " + rightY;
    };
}
