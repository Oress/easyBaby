import {InventoryItem} from './InventoryItem';
import {ProductImage} from './ProductImage';

export interface Product {
  id?: number;
  title?: string;
  description?: string;
  price?: number;
  registrationDate?: number;
  addedById?: number;
  // productCharValues: number;
  categoryIds?: number[];
  inventoryItems?: InventoryItem[];
  images?: ProductImage[];
  image?: ProductImage;
}
