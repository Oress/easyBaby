import { Component, OnInit } from '@angular/core';
import { ProductWebService } from '../services/web/product-web.service';
import { Store } from '@ngrx/store';
import { AppState } from '../store/AppState';
import { selectFavs } from '../store/appSelectors';
import { Product } from '../model/Product';
import { RemoveFromFav, SetFavs } from '../store/appActions';
import { CartItem } from '../model/CartItem';

@Component({
  selector: 'app-favourites',
  templateUrl: './favourites.component.html',
  styleUrls: ['./favourites.component.scss']
})
export class FavouritesComponent implements OnInit {
  products: Product[];

  constructor(
    private productService: ProductWebService,
    private store: Store<AppState>) {
  }

  ngOnInit(): void {
    this.store.select(selectFavs).subscribe(favs => {
      if (favs && favs.length) {
        this.productService.getFavs(favs).subscribe(res => {
          this.products = res;
        });
      } else {
        this.products = [];
      }
    });
  }

  removeFromFavs(item: Product): void {
    console.log(item);
    this.store.dispatch(new RemoveFromFav(item.id));
  }
}
