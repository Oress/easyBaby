import { CartItem } from './CartItem';
import { PaymentInfo } from './PaymentInfo';
import { DeliveryInfo } from './DeliveryInfo';
import { ProductImage } from './ProductImage';

export interface MyOrderEntry {
    productId: number;
    productTitle: string;
    image: ProductImage;
    count: number;
    barcodes: string[];
}
