export interface CartItem {
    id?: number;
    productId?: number;
    image?: string;
    productTitle?: string;
    count?: number;
    pricePerItem?: number;
    totalPrice?: number;
    isAvailable?: boolean;
}
