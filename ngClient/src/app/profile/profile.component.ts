import {Component, OnInit} from '@angular/core';
import {AppState} from '../store/AppState';
import {Store} from '@ngrx/store';
import {isStaff, selectCountInCart, selectCountInFavs} from '../store/appSelectors';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  isStaff = false;
  cartCount = 0;
  favsCount = 0;

  constructor(private store: Store<AppState>) {
  }

  ngOnInit(): void {
    this.store.select(isStaff).subscribe(data => this.isStaff = data);
    this.store.select(selectCountInCart).subscribe(c => this.cartCount = c);
    this.store.select(selectCountInFavs).subscribe(c => this.favsCount = c);
  }
}
