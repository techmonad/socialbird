import { Component, ElementRef, Input, ViewChild, AfterViewInit, Directive, ViewContainerRef, Inject, OnInit } from "@angular/core";


@Component({
  selector: 'halfdoughnut',
  styles:[` .chart {display: block; width: 100%;}`],

  template: `
    <div style="display: block">
    <canvas baseChart
          [data]="doughnutChartData"
          [labels]="doughnutChartLabels"
          [chartType]="doughnutChartType"
          [options]="options"
          (chartHover)="chartHovered($event)"
          (chartClick)="chartClicked($event)"></canvas>
    </div>
  `
})
export class HalfDoughnutChart {
  public doughnutChartLabels:string[] = ['A', 'B'];
  public doughnutChartData:number[] = [450, 350];
  public doughnutChartType:string = 'doughnut';
  public options = {
        cutoutPercentage: 80,
      	rotation: 1 * Math.PI,
        circumference: 1 * Math.PI,
        legend: {
            display: false,
            labels: {
                fontColor: 'rgb(255, 99, 132)'
            }
        },
        tooltips:false,
        title: {
            display: false,
            text: 'Custom Chart Title',
            position: 'bottom'
        }
      }
  // events
  public chartClicked(e:any):void {
    console.log(e);
  }

  public chartHovered(e:any):void {
    console.log(e);
  }
}
