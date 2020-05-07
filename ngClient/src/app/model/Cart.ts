import { CartItem } from './CartItem';

export interface Cart {
    available: CartItem[];
    totalPrice: number;
}
