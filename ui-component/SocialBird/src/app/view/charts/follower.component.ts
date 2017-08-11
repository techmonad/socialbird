import {Component, OnInit, Input} from "@angular/core";
import { DataService } from '../../provider/data.service';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'chart-followers',
styles:[`
   .chart {display: block; width: 100%;}`],

  template: `

  <div style="display: block">

    <canvas height="100" baseChart
               [data]="lineChartData"
               [labels]="lineChartLabels"
               [options]="lineChartOptions"
               [chartType]="lineChartType" ></canvas>
  </div>
  `
})
export class FollowersChart implements OnInit {



  constructor(public dataService: DataService) { }

  ngOnInit(): void {
    console.log('hi');
    this.dataService.getPoliticians().subscribe(res => {
      this.drawFollowersCharts(res.single);
    });
  }

  drawFollowersCharts(data:any){

  }
  public lineChartOptions = {
    title: {
      display: false,
    },
    legend: {
      display: true,
      position: 'bottom',
      labels: {
        fontColor: 'rgb(255, 99, 132)'
      }
    },
    tooltips:false,
  }

  // lineChart
  public lineChartData:Array<any> = [
    [65, 59, 80, 81, 56, 55, 40]
  ];
  public lineChartLabels:Array<any> = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];
  public lineChartType:string = 'line';

}
