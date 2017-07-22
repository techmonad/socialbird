import { Injectable } from '@angular/core';
import {Http, Headers, RequestOptions, Response} from "@angular/http";
import { parse } from 'date-fns';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/toPromise';
import { environment } from '../../environments/environment';

@Injectable()
export class DataService {

  data: any;
  private API_PATH: string = environment.path.api.service;
  constructor(public http: Http) { }

  load(): any {
    if (this.data) {
      return Observable.of(this.data);
    } else {
      return this.http.get('assets/data/data.json');//.map(this., this);
    }
  }

    // load(){
    //     return this.http.get(`${this.API_PATH}/app/onload`);
    // }

  //


  getFollowersGraph(): Observable<any> {
    return this.http.get('assets/data/data.json').map(this.processResponse);//.catch(this.handleError);
  }
  getPoliticians(): Observable<any> {
    return this.http.get('assets/data/data.json').map(this.processResponse);//.catch(this.handleError);
  }

  getPoliticians1(): Observable<any> {
    return this.http.get('assets/data/data.json').map(this.processResponse);//.catch(this.handleError);
  }

  // loadGameList(): Observable<any[]> {
  //   return this.http.get(`${this.API_PATH}/game/list`).map(this.processResponse).catch(this.handleError);
  // }


  private handleError(error: Response | any): Observable<any> {
    // if ((error.status === 400 || error.status === 401 || error.status === 403) && (window.location.href.match(/\?/g) || []).length < 2) {
    //     console.log('The authentication session expires or the user is not authorised. Force refresh of the current page.');
    //     //window.location.href = window.location.href + '?' + new Date().getMilliseconds();
    // }

    let errMsg: string;
    if (error instanceof Response) {
      if(error.status == 0){
        console.log('SocialGameGerver Down');
        alert('Not able to connect to the server, Please try after sometime.');
      }
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Observable.throw(error.json().error || 'Server error');
  }

  private processResponse(response: any):any {
    if(response.status == 404) {
      throw new Error('This request has failed ');
    }else if(response.status == 204 || response.status < 200 || response.status >= 300) {
      throw new Error('This request has failed ' + response.status);
    } else {
      if(response.text()){
        return response.json();
      }else{
        console.log("*****empty json******");
        return ;//'{}';
         //return Observable.of<Hero[]>([]);
      }
    }
  }
  public extractData(res: Response) {
      let body = res.json();
      return body || {};
  }

}
