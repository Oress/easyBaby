import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category } from '../../model/Category';
import { Product } from '../../model/Product';
import { Cart } from 'src/app/model/Cart';
import { CartItem } from 'src/app/model/CartItem';
import { Order } from 'src/app/model/Order';
import { OrderResult } from 'src/app/model/OrderResult';

@Injectable({
  providedIn: 'root'
})
export class OrderWebService {
  constructor(private http: HttpClient) {
  }

  public order(order: Order): Observable<OrderResult> {
    return this.http.post<OrderResult>('/api/order', order);
  }

  public getMyOrders(): Observable<Order[]> {
    return this.http.get<Order[]>('/api/order/my');
  }

  public getCount(): Observable<number> {
    return this.http.get<number>('/api/product/count');
  }

  public getByCategoryId(id: string): Observable<Product[]> {
    return this.http.get<Product[]>('/api/product/byCategory', { params: { id } });
  }

  public getById(id: number): Observable<Product> {
    return this.http.get<Product>('/api/product/byId', { params: { id: id.toString() } });
  }

  public getCart(items: CartItem[]): Observable<Cart> {
    return this.http.post<Cart>('/api/product/cart', items);
  }

  public getFavs(items: number[]): Observable<Product[]> {
    const vals = items.map(item => item.toString());
    return this.http.get<Product[]>('/api/product/favs', { params: { prodIds: vals } });
  }

}
