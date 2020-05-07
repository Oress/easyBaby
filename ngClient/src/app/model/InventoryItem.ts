import {InventoryItemStatus} from './InventoryItemStatus';

export interface InventoryItem {
  id?: number;
  barcode: string;
  productId: number;
  addedById?: number;
  registrationDate?: Date;
  status: InventoryItemStatus;
}

