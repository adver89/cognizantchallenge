import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {SubmissionPageComponent} from './submission-page/submission-page.component';
import {ScoresPageComponent} from './scores-page/scores-page.component';

const routes: Routes = [
  {path: 'submission', component: SubmissionPageComponent},
  {path: 'scores', component: ScoresPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
