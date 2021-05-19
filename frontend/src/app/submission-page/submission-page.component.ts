import {Component, OnDestroy, OnInit} from '@angular/core';
import { TaskService } from '../_services/task.service';
import {MatSnackBar} from "@angular/material/snack-bar";
import {Subscription} from "rxjs";
import { Task } from '../_model/task';
import {Submission} from "../_model/submission";

@Component({
  selector: 'app-submission-page',
  templateUrl: './submission-page.component.html',
  styleUrls: ['./submission-page.component.css']
})
export class SubmissionPageComponent implements OnInit, OnDestroy {

  tasks: Task[];
  selectedTask: Task = new Task();
  submission: Submission = new Submission();
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
    console.log('---> submit code:', this.submission);
    this.submission.language = 'nodejs';
    this.submission.taskId = this.selectedTask.id;
    this.submissionSubscription = this.taskService.submitCode(this.submission).subscribe(response => {
      console.log('code sent', response);
      this.snackBar.open(response ? 'Solution correct' : 'Solution wrong', null, {duration: 5000});
    });
  }

  ngOnDestroy(): void {
    if (this.submissionSubscription) {
      this.submissionSubscription.unsubscribe();
    }
    if (this.tasksSubscription) {
      this.tasksSubscription.unsubscribe();
    }
  }
}
