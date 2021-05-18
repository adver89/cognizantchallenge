import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor(private httpClient: HttpClient) { }

  getTasks() {
    return this.httpClient.get<any[]>('api/tasks');
  }

  submitCode(submission: any) {
    return this.httpClient.post<any>('api/submissions', submission);
  }

}
