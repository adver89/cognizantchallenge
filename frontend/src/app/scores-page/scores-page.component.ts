import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {ScoreService} from "../_services/score.service";

@Component({
  selector: 'app-scores-page',
  templateUrl: './scores-page.component.html',
  styleUrls: ['./scores-page.component.css']
})
export class ScoresPageComponent implements OnInit, OnDestroy {
  scores: any[];
  private bestScoresSubscription: Subscription;

  constructor(private scoreService: ScoreService) {
  }

  ngOnInit(): void {
    this.bestScoresSubscription = this.scoreService.getBestScores().subscribe(response => {
      this.scores = response;
    });
  }

  ngOnDestroy(): void {
    this.bestScoresSubscription.unsubscribe();
  }

}
