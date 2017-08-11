import {Component, OnInit, Input} from "@angular/core";
import { DataService } from '../../provider/data.service';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'chart-tweets',
styles:[`
   .chart {display: block; width: 100%;}`],

  template: `

  <div style="display: block">
    <canvas height="100" baseChart
        [datasets]="lineChartData"
        [labels]="lineChartLabels"
        [options]="lineChartOptions"

        [legend]="lineChartLegend"
        [chartType]="lineChartType" >
    </canvas>
  </div>
  `
})
export class TweetsChart implements OnInit {



  constructor(public dataService: DataService) { }

  ngOnInit(): void {
    console.log('hi');
    this.dataService.getPoliticians().subscribe(res => {
      this.drawFollowersCharts(res.single);
    });
  }

  drawFollowersCharts(data:any){

  }

  public lineChartLegend:boolean = true;
      public lineChartType:string = 'line';
      public randomize():void {
        let _lineChartData:Array<any> = new Array(this.lineChartData.length);
        for (let i = 0; i < this.lineChartData.length; i++) {
          _lineChartData[i] = {data: new Array(this.lineChartData[i].data.length), label: this.lineChartData[i].label};
          for (let j = 0; j < this.lineChartData[i].data.length; j++) {
            _lineChartData[i].data[j] = Math.floor((Math.random() * 100) + 1);
          }
        }
        this.lineChartData = _lineChartData;
      }
    // lineChart
    public lineChartData:Array<any> = [
      {data: [0, 0, 0, 0, 0, 0, 0, 65, 59, 80, 65, 59, 5, 4, 56, 55, 40], label: 'Desktop Plugload'}
    ];
    public lineChartLabels:Array<any> = ['00:00','01:00', '02:00', '03:00', '04:00', '05:00', '06:00', '07:00', '08:00', '09:00','10:00', '11:00', '12:00',
                                         '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00', '21:00', '22:00', '23:00', '24:00'];
    public lineChartOptions:any = {
      animation: false,
      responsive: true
    };
    public lineChartColors:Array<any> = [
      {
        backgroundColor: "rgba(38, 185, 154, 0.31)",
        borderColor: "rgba(38, 185, 154, 0.7)",
        pointBorderColor: "rgba(38, 185, 154, 0.7)",
        pointBackgroundColor: "rgba(38, 185, 154, 0.7)",
        pointHoverBackgroundColor: "#fff",
        pointHoverBorderColor: "rgba(220,220,220,1)",
        pointBorderWidth: 1,
      }, {
        fillColor: "rgba(60,141,188,0.9)",
        strokeColor: "rgba(60,141,188,0.8)",
        pointColor: "#3b8bba",
        pointStrokeColor: "rgba(60,141,188,1)",
        pointHighlightFill: "#fff",
        pointHighlightStroke: "rgba(60,141,188,1)",
      }, {
        backgroundColor: 'rgba(77,83,96,0.2)',
        borderColor: 'rgba(77,83,96,1)',
        pointBackgroundColor: 'rgba(77,83,96,1)',
        pointBorderColor: '#fff',
        pointHoverBackgroundColor: '#fff',
        pointHoverBorderColor: 'rgba(77,83,96,1)'
      }, {
        backgroundColor: 'rgba(148,159,177,0.2)',
        borderColor: 'rgba(148,159,177,1)',
        pointBackgroundColor: 'rgba(148,159,177,1)',
        pointBorderColor: '#fff',
        pointHoverBackgroundColor: '#fff',
        pointHoverBorderColor: 'rgba(148,159,177,0.8)'
      }
    ];
}
