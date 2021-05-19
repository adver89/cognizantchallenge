import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { Task } from '../_model/task';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor(private httpClient: HttpClient) { }

  getTasks() {
    return this.httpClient.get<Task[]>('api/tasks');
  }

  submitCode(submission: any) {
    return this.httpClient.post<boolean>('api/submissions', submission);
  }

}
