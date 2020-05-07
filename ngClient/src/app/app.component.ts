import { Component, ViewChild, ElementRef } from '@angular/core';
import { LocalStorageService } from 'ngx-webstorage';
import { Constants } from './shared/constants';
import { Store } from '@ngrx/store';
import { AppState } from './store/AppState';
import { selectFavs, selectCart } from './store/appSelectors';
import { SetFavs, SetCart } from './store/appActions';
import { CartItem } from './model/CartItem';
import { SearchComponent } from './search/search.component';
import { Router } from '@angular/router';
import { CategoryUrlFormatter } from './shared/category-url-formatter';
import { Category } from './model/Category';
import { Subject } from 'rxjs/internal/Subject';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { Product } from './model/Product';
import { InputText } from 'primeng';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  @ViewChild('searchComp') searchComp: SearchComponent;
  @ViewChild('inp') inputText: ElementRef;
  private cuf: CategoryUrlFormatter = new CategoryUrlFormatter();
  showSearch = false;

  modelChanged: Subject<string> = new Subject<string>();
  query = '';

  constructor(
    private localSt: LocalStorageService,
    private store: Store<AppState>,
    private router: Router,
  ) {
    this.useLocalStorage();
    this.modelChanged.pipe(debounceTime(300)).subscribe(model => this.searchComp.search(model));
  }

  changed(text: string) {
    this.modelChanged.next(text);
  }

  selectCategory(selectedCat: Category) {
    this.closeSearch();
    this.router.navigate([this.cuf.createLink(selectedCat)]);
  }

  selectProduct(selectedProduct: Product) {
    this.closeSearch();
    this.router.navigate(['/product/' + selectedProduct.id]);
  }

  closeSearch() {
    this.showSearch = false;
    this.inputText.nativeElement.value = '';
  }

  private useLocalStorage() {
    const vals: number[] = this.localSt.retrieve(Constants.LS_FAVS);
    if (vals) {
      console.log('Retrieved');
      this.store.dispatch(new SetFavs(vals));
    }
    this.store.select(selectFavs).subscribe(val => {
      console.log('Stored Favs');

      this.localSt.store(Constants.LS_FAVS, val);
    });

    const cart: CartItem[] = this.localSt.retrieve(Constants.LS_CART);
    if (cart) {
      console.log('Retrieved CART');
      this.store.dispatch(new SetCart(cart));
    }
    this.store.select(selectCart).subscribe(val => {
      console.log('Stored Cart');

      this.localSt.store(Constants.LS_CART, val);
    });
  }
}
