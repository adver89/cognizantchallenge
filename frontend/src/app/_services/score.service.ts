import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ScoreService {

  constructor(private httpClient: HttpClient) {
  }

  getBestScores() {
    return this.httpClient.get<any[]>('api/scores');
  }
}
