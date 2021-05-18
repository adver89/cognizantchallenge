import {Component, OnDestroy, OnInit} from '@angular/core';
import { TaskService } from '../_services/task.service';
import {MatSnackBar} from "@angular/material/snack-bar";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-submission-page',
  templateUrl: './submission-page.component.html',
  styleUrls: ['./submission-page.component.css']
})
export class SubmissionPageComponent implements OnInit, OnDestroy {

  tasks = [];
  selectedTask: any;
  submission: any = {};
  languages: any = [{caption: 'Java', value: 'java'}, {caption: 'JSNode', value: 'jsnode'}];
  private submissionSubscription: Subscription;
  private tasksSubscription: Subscription;

  constructor(private taskService: TaskService, private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.tasksSubscription = this.taskService.getTasks().subscribe(response => {
      this.tasks = response;
    });
  }

  submitCode(): void {
    this.submission.language = 'nodejs';
    this.submission.taskId = this.selectedTask.id;
    this.submissionSubscription = this.taskService.submitCode(this.submission).subscribe(response => {
      console.log('code sent', response);
      this.snackBar.open('Result: ' + response.correct, null, {duration: 5000});
    });
  }

  ngOnDestroy(): void {
    this.submissionSubscription.unsubscribe();
    this.tasksSubscription.unsubscribe();
  }
}
