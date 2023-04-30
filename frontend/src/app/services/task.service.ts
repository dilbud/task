import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observer } from 'rxjs/internal/types';
import { BehaviorSubject, tap } from 'rxjs';
import { Event } from "../common/event";
import * as moment from 'moment';


export class EventObs implements Observer<Event[]>{
  private _eventList: Event[] = [];
  private _eventLoadedSubject: BehaviorSubject<boolean>;
  constructor(eventList: Event[], eventLoadedSubject: BehaviorSubject<boolean>) {
    this._eventList = eventList;
    this._eventLoadedSubject = eventLoadedSubject;
  }

  next = (v: Event[]) => {
    this._eventList.push(...v);
  };
  error = (e: any) => console.error(e);
  complete = () => this._eventLoadedSubject.next(true);

  get eventList() {
    return this._eventList;
  }
} 

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private _eventList: Event[] = [];
  private _eventLoadedSubject: BehaviorSubject<boolean> = new BehaviorSubject(false);
  public isLoaded = false;

  constructor(
    private http: HttpClient
  ) { }

  public loadEvents() {
    const eventObs = new EventObs(this._eventList, this._eventLoadedSubject);
    return this.http.get<Event[]>(environment.apiUrl.concat('/events')).subscribe(eventObs);
  }

get eventList() {
    return this._eventList;
  }

get eventDateList() {
  return this._eventList.map((event: Event) => event.date);
}

get eventLoadedSubject() {
  return this._eventLoadedSubject.pipe(tap((val: boolean)=> this.isLoaded = val));
}

  public generateEndDate(startDate: Date, days: number) {
    console.log();
    let params = new HttpParams();

    // Begin assigning parameters
    params = params.append('startDate', moment(startDate).format('YYYY-MM-DD'));
    params = params.append('days', days);
    return this.http.get<string>(environment.apiUrl.concat('/tasks'), { params: params })
  }
}

