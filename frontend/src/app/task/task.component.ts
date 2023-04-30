import { ChangeDetectorRef, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { TaskService } from '../services/task.service';
import { DateRange, MatCalendar, MatCalendarCellClassFunction } from '@angular/material/datepicker';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { Event } from "../common/event";
import * as moment from 'moment';


@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.scss']
})
export class TaskComponent implements OnInit, OnDestroy {

  selection: DateRange<Date> | Date = new DateRange(new Date(), new Date());
  taskRange: DateRange<Date | null> = new DateRange(null, null);

  // selectedDate: Date = new Date();

  private subscriptions: Subscription[] = [];


  datesToHighlight: string[] = [];

  form = new FormGroup({
    startDate: new FormControl<Date| null>({disabled: false, value: null}, [
      Validators.required
    ]),
    days: new FormControl<number| null>({disabled: false, value: null}, [Validators.required, Validators.min(1)])
  });
  result = new FormControl<Date | null>({ disabled: true, value: null });

  dayDescription = '';
  day = '';

  constructor(
    public taskService: TaskService
  ) {
  }

  ngOnInit() {
    this.subscriptions.push(this.taskService.loadEvents());
    this.subscriptions.push(this.taskService.eventLoadedSubject.subscribe((isLoaded: boolean) =>{
      if (isLoaded) {
        this.datesToHighlight.push(...this.taskService.eventDateList);
      }
    }))
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription: Subscription) => {
      subscription.unsubscribe();
    })
  }

  public onSelect(event: any) {
    // this.selection = new DateRange(event, event);
    const eventDay = this.taskService.eventList.filter((day: Event) => moment(day.date).isSame(event))[0];
    this.dayDescription = eventDay?.description ?? '';
    this.day = eventDay?.date ?? '';
  }

  private weekendsDatesFilter(d: Date): boolean {
    const day = d.getDay();
    return day !== 0 && day !== 6;
  }

  public dateClass: MatCalendarCellClassFunction<Date> = (date, view) => {
    if (view === 'month') {
      const highlightDate = this.datesToHighlight
        .map(strDate => new Date(strDate))
        .some(d => d.getDate() === date.getDate() && d.getMonth() === date.getMonth() && d.getFullYear() === date.getFullYear());
      return highlightDate ? 'special-date' : (!this.weekendsDatesFilter(date) ? "week-ends" : "");
    }
    return '';
  };

  public dateFilter = (date: Date): boolean => {
    return true;
    const d = new Date();
    return new Date(date.getFullYear(), date.getMonth(), date.getDate()) >= new Date(d.getFullYear(), d.getMonth(), d.getDate());
  }

  // public keydown = (e: any) => {
  //   console.log(e);
    
  //   if (!((e.keyCode > 95 && e.keyCode < 106)
  //     || (e.keyCode > 47 && e.keyCode < 58)
  //     || e.keyCode == 8)) {
  //     return false;
  //   } else {
  //     return true;
  //   }
  // }

  public submit() {
    this.subscriptions.push(this.taskService
      .generateEndDate(this.form.value.startDate as Date, this.form.value.days as number).subscribe((date: string)=>{
      this.result.setValue(new Date(date))
      this.result.enable();
      this.taskRange = new DateRange(this.form.value.startDate as Date, new Date(date));
      this.selection = new DateRange(new Date(date), new Date(date));
    }));
  }

  public reset() {
    this.form.reset();
    this.result.reset();
    this.taskRange = new DateRange(null, null);
    this.selection = new Date();
    this.dayDescription = '';
    this.day = '';
  }
}
