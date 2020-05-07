import { Component, OnInit } from '@angular/core';
import { Store, resultMemoize } from '@ngrx/store';
import { AppState } from '../store/AppState';
import { Cart } from '../model/Cart';
import { selectCart, selectUserData } from '../store/appSelectors';
import { ProductWebService } from '../services/web/product-web.service';
import { User } from '../model/user';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { OrderWebService } from '../services/web/order-web.service';
import { DeliveryInfo } from '../model/DeliveryInfo';
import { PaymentInfo } from '../model/PaymentInfo';
import { CartItem } from '../model/CartItem';
import { Order } from '../model/Order';
import { DeliveryType } from '../model/DeliveryType';
import { PaymentType } from '../model/PaymentType';
import { Router } from '@angular/router';
import { ClearCart } from '../store/appActions';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss']
})
export class CheckoutComponent implements OnInit {
  cart: Cart;
  user: User;

  contactInfoForm: FormGroup;
  deliveryInfoForm: FormGroup;
  paymentInfoForm: FormGroup;

  deliveryType = DeliveryType;
  paymentType = PaymentType;

  addresses: any[] = [{ label: '' }];

  constructor(
    private store: Store<AppState>,
    private productService: ProductWebService,
    private orderService: OrderWebService,
    private fb: FormBuilder,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.buildForms();
    this.store.select(selectCart).subscribe(cartItems => {
      this.productService.getCart(cartItems).subscribe(item => {
        this.cart = item;
      });
    });
    this.store.select(selectUserData).subscribe(userData => {
      this.user = userData;
      if (userData && userData.contactInfo) {
        let name = '';
        if (userData.contactInfo.firstName) name += userData.contactInfo.firstName + ' ';
        if (userData.contactInfo.lastName) name += userData.contactInfo.lastName + ' ';
        if (userData.contactInfo.middleName) name += userData.contactInfo.middleName + ' ';
        this.contactInfoForm.controls.name.setValue(name);

        this.contactInfoForm.controls.phone.setValue(userData?.contactInfo?.phone1);
      }
    });
  }

  private buildForms() {
    this.contactInfoForm = this.fb.group({
      name: ['', [Validators.required]],
      phone: ['', [Validators.required]],
      address: ['', [Validators.required]],
    }, { updateOn: 'blur' });

    this.deliveryInfoForm = this.fb.group({
      type: ['', [Validators.required]],
    }, { updateOn: 'blur' });

    this.paymentInfoForm = this.fb.group({
      type: ['', [Validators.required]],
    }, { updateOn: 'blur' });
  }

  order() {
    this.contactInfoForm.markAsDirty();
    this.deliveryInfoForm.markAsDirty();
    this.paymentInfoForm.markAsDirty();

    if (this.contactInfoForm.valid
      && this.deliveryInfoForm.valid
      && this.paymentInfoForm.valid) {
      const delivery: DeliveryInfo = {
        name: this.contactInfoForm.controls.name.value,
        phone: this.contactInfoForm.controls.phone.value,
        address: this.contactInfoForm.controls.address.value,
        type: this.deliveryInfoForm.controls.type.value,
      };

      const payment: PaymentInfo = {
        type: this.paymentInfoForm.controls.type.value,
      };

      const order: Order = {
        cartItems: this.cart.available,
        shipmentInfo: delivery,
        paymentInfo: payment
      };

      this.orderService.order(order).subscribe(res => {
        if (!res.problems || res.problems.length === 0) {
          console.log(res);
          this.store.dispatch(new ClearCart());
          this.router.navigate(['profile/orders'])
        }
      });
    }
  }
}
