import {Component, OnInit} from '@angular/core';
import {User} from '../model/user';
import {Store} from '@ngrx/store';
import {AppState} from '../store/AppState';
import {selectUserData} from '../store/appSelectors';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  totalBasketCost = 0;
  displayLogin = false;
  user: User;

  constructor(private store: Store<AppState>,
              private authService: Store<AppState>) {
  }

  ngOnInit() {
    this.store.select(selectUserData).subscribe(data => {
      this.user = data;
    });
  }

}
