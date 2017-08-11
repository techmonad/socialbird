import { Injectable, APP_INITIALIZER  } from '@angular/core';
import {Http, Headers, RequestOptions, Response} from "@angular/http";
import { environment } from '../../environments/environment';
import { DataService } from './data.service';

@Injectable()
export class ConfigService {

    private _config: any;
    private API_PATH: string = environment.path.api.service;
    constructor(private http: Http){ }

    load(): Promise<any>{
      return this.http.get(`${this.API_PATH}/app/onload`)
        .map( (response: Response) => response.json())
        .toPromise().then(data => {
          this._config = data;
          return data;
        });
    }
    get config(): any {
      return this._config;
    }
}

export function startupFactory(configService: ConfigService) { return () => console.log('hi'); }//configService.load(); }

export const INIT_INITIALIZER = {
  provide: APP_INITIALIZER,
  useFactory: startupFactory,
  deps: [ConfigService],
  multi: true
};
