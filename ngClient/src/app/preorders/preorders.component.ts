import { Component, OnInit } from '@angular/core';
import { DeliveryType } from '../model/DeliveryType';
import { PaymentType } from '../model/PaymentType';
import { Order } from '../model/Order';
import { PreorderWebService } from '../services/web/preorder-web.service';
import { Preorder } from '../model/Preorder';
import { PreorderTypePeriod } from '../model/PreorderTypePeriod';

@Component({
  selector: 'app-preorders',
  templateUrl: './preorders.component.html',
  styleUrls: ['./preorders.component.scss']
})
export class PreordersComponent implements OnInit {
  preorders: Preorder[];
  deliveryType = DeliveryType;
  paymentType = PaymentType;
  preorderTypePeriod = PreorderTypePeriod;

  constructor(
    private preorderWebService: PreorderWebService
  ) { }

  ngOnInit(): void {
    this.preorderWebService.getMyOrders().subscribe(res => {
      console.log(res);
      this.preorders = res;
    });
  }

  getOrderHeader(order: Preorder) {
    return 'Preorder #' + order.id;
  }
}
