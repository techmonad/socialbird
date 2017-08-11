import { Component, OnInit } from '@angular/core';
import { DataService } from '../../../provider/data.service';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-section',
  templateUrl: './section.component.html',
  styleUrls: ['./section.component.css'],

})
export class SectionComponent implements OnInit {

  constructor(public dataService: DataService) { }
  //public politiciansList: Observable<any>;
  public politiciansList: any;
  public politiciansList1: any;


  ngOnInit() {
    this.getTopPoliticians();
    this.getTopPoliticians1();
  }


  getTopPoliticians1() {
    this.dataService.getPoliticians().subscribe(res => {
      this.politiciansList1 = res.politicians1 ;
    });
  }
  getTopPoliticians() {
    this.dataService.getPoliticians().subscribe(res => {
      this.politiciansList = res.politicians; 
    });
  }
}
