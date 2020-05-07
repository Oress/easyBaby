import { Order } from './Order';

export interface OrderResult {
    orderItems: Order;
    problems: string[];
}
