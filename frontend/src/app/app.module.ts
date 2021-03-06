import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {SubmissionPageComponent} from './submission-page/submission-page.component';
import {ScoresPageComponent} from './scores-page/scores-page.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatOptionModule} from '@angular/material/core';
import {MatSelectModule} from '@angular/material/select';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatButtonModule} from "@angular/material/button";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import { ValidationMessageComponent } from './_commons/validation-message/validation-message.component';
import {MatDividerModule} from "@angular/material/divider";
import { WelcomePageComponent } from './welcome-page/welcome-page.component';


@NgModule({
  declarations: [
    AppComponent,
    SubmissionPageComponent,
    ScoresPageComponent,
    ValidationMessageComponent,
    WelcomePageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatOptionModule,
    MatSelectModule,
    MatInputModule,
    HttpClientModule,
    FormsModule,
    FlexLayoutModule,
    MatButtonModule,
    MatSnackBarModule,
    MatDividerModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
