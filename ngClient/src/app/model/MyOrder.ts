import { CartItem } from './CartItem';
import { PaymentInfo } from './PaymentInfo';
import { DeliveryInfo } from './DeliveryInfo';
import { MyOrderEntry } from './MyOrderEntry';

export interface MyOrder {
    id?: number;
    totalPrice?: number;
    orderItems?: MyOrderEntry[];
    shipmentInfo?: DeliveryInfo;
    paymentInfo?: PaymentInfo;
}
