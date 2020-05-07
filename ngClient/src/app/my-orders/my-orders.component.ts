import { Component, OnInit } from '@angular/core';
import { OrderWebService } from '../services/web/order-web.service';
import { Order } from '../model/Order';
import { DeliveryType } from '../model/DeliveryType';
import { PaymentType } from '../model/PaymentType';
import { PreorderTypePeriod } from '../model/PreorderTypePeriod';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { PreorderWebService } from '../services/web/preorder-web.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-my-orders',
  templateUrl: './my-orders.component.html',
  styleUrls: ['./my-orders.component.scss']
})
export class MyOrdersComponent implements OnInit {
  orders: Order[];
  deliveryType = DeliveryType;
  paymentType = PaymentType;
  displayPreorder = false;

  periods = [
    { label: 'Week', value: PreorderTypePeriod.WEEK },
    { label: 'Month', value: PreorderTypePeriod.MONTH },
    { label: 'Quarter', value: PreorderTypePeriod.QUARTER },
  ];

  preorderForm: FormGroup;

  selectedOrder: Order;

  constructor(
    private orderService: OrderWebService,
    private preorderWebService: PreorderWebService,
    private fb: FormBuilder,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.orderService.getMyOrders().subscribe(res => {
      console.log(res);
      this.orders = res;
    });

    this.preorderForm = this.fb.group({
      count: [1, [Validators.required]],
      period: [this.periods[0], [Validators.required]]
    });
  }

  getOrderHeader(order: Order) {
    return 'Order #' + order.id;
  }

  showPreorderDialog(order: Order) {
    this.displayPreorder = true;
    this.selectedOrder = order;
  }

  makePreorder() {
    this.preorderWebService.preorder({
      orderId: this.selectedOrder.id,
      timePeriod: {
        count: this.preorderForm.controls.count.value,
        timePeriod: this.preorderForm.controls.period.value.value
      }
    }).subscribe(res => {
      console.log(res);
      this.displayPreorder = false;
      this.router.navigate(['/profile/preorders']);
    });
  }
}
