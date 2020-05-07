import { DeliveryType } from './DeliveryType';

export interface DeliveryInfo {
    name: string;
    phone: string;
    address: string;
    type: DeliveryType;
}
