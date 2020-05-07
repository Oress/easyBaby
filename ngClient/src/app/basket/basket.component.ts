import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { ConfirmationService, MenuItem } from 'primeng';
import { Cart } from '../model/Cart';
import { CartItem } from '../model/CartItem';
import { ProductWebService } from '../services/web/product-web.service';
import { ClearCart, RemoveFromCart, SetCartItem } from '../store/appActions';
import { selectCart } from '../store/appSelectors';
import { AppState } from '../store/AppState';
import { stringify } from 'querystring';

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.scss']
})
export class CartComponent implements OnInit {

  constructor(
    private store: Store<AppState>,
    private router: Router,
    private productService: ProductWebService,
    private confirmationService: ConfirmationService
  ) { }

  loading = false;
  cart: Cart;
  menuItems: MenuItem[];
  canPlaceOrder = false;

  ngOnInit() {
    this.menuItems = [
      { label: 'Order' },
      { label: 'Shipment' },
      { label: 'Payment' },
      { label: 'Confirmation' }
    ];

    this.store.select(selectCart).subscribe(cart => {
      this.validateCartItems(cart);
    });
  }

  validateCartItems(cartItems: CartItem[], onValdidationEnd: () => void = null) {
    console.log('Validating cart', cartItems);
    if (cartItems) {
      this.loading = true;
      this.productService.getCart(cartItems).subscribe(item => {
        this.cart = item;
        this.sortCartItems();
        this.canPlaceOrder = this.cart.available && this.cart.available.findIndex(item => !item.isAvailable) === -1;
        this.loading = false;
        if (onValdidationEnd) {
          onValdidationEnd();
        }
      });
    }
  }

  changeCount(item: CartItem) {
    this.store.dispatch(new SetCartItem(item.productId, item.count));
  }

  clearCart() {
    this.confirmationService.confirm({
      message: 'Are you sure that you want to clear cart?',
      accept: () => {
        this.store.dispatch(new ClearCart());
      }
    });
  }

  removeFromCart(item: CartItem) {
    console.log(item);
    this.store.dispatch(new RemoveFromCart(item.productId));
  }

  palceOrder() {
    this.validateCartItems(this.cart.available, () => {
      // if (!this.cart.unavailable || this.cart.unavailable.length === 0) {
      //   this.router.navigate(['/checkout']);
      // }
      if (this.cart.available && this.cart.available.findIndex(item => !item.isAvailable) === -1) {
        this.router.navigate(['/checkout']);
      }
    });
  }

  sortCartItems() {
    this.cart.available.sort((a, b) => a.productTitle.localeCompare(b.productTitle));
  }
}
