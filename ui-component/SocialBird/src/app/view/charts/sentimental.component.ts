import {Component, OnInit, Input} from "@angular/core";
import { DataService } from '../../provider/data.service';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'chart-sentimental',
styles:[`
   .chart {display: block; width: 100%;}`],

  template: `

  <div style="display: block">
  <canvas height="100" baseChart
              [data]="pieChartData"
              [labels]="pieChartLabels"
              [chartType]="pieChartType"
              (chartHover)="chartHovered($event)"
              (chartClick)="chartClicked($event)"></canvas>
  </div>
  `
})
export class SentimentalChart implements OnInit {



  constructor(public dataService: DataService) { }

  ngOnInit(): void {
    console.log('hi');
    this.dataService.getPoliticians().subscribe(res => {
      this.drawFollowersCharts(res.single);
    });
  }

  drawFollowersCharts(data:any){

  }

  // lineChart
  public lineChartData:Array<any> = [
    [65, 59, 80, 81, 56, 55, 40],
    [28, 48, 40, 19, 86, 27, 90]
  ];
  public lineChartLabels:Array<any> = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];
  public lineChartType:string = 'line';
  public pieChartType:string = 'doughnut';

  // Pie
  public pieChartLabels:string[] = ['Download Sales', 'In-Store Sales'];
  public pieChartData:number[] = [300, 500];

  public randomizeType():void {
    this.lineChartType = this.lineChartType === 'line' ? 'bar' : 'line';
    this.pieChartType = this.pieChartType === 'doughnut' ? 'pie' : 'doughnut';
  }

  public chartClicked(e:any):void {
    console.log(e);
  }

  public chartHovered(e:any):void {
    console.log(e);
  }
}
