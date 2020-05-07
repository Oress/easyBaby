import { CartItem } from './CartItem';
import { PaymentInfo } from './PaymentInfo';
import { DeliveryInfo } from './DeliveryInfo';

export interface Order {
    id?: number;
    totalPrice?: number;
    cartItems?: CartItem[];
    orderItems?: CartItem[];
    shipmentInfo?: DeliveryInfo;
    paymentInfo?: PaymentInfo;
}
