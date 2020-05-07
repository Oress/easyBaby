import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category } from '../../model/Category';
import { Product } from '../../model/Product';
import { Cart } from 'src/app/model/Cart';
import { CartItem } from 'src/app/model/CartItem';
import { Order } from 'src/app/model/Order';
import { OrderResult } from 'src/app/model/OrderResult';
import { Preorder } from 'src/app/model/Preorder';

@Injectable({
  providedIn: 'root'
})
export class PreorderWebService {
  constructor(private http: HttpClient) {
  }

  public preorder(order: any): Observable<any> {
    return this.http.post<any>('/api/preorder', order);
  }

  public getMyOrders(): Observable<Preorder[]> {
    return this.http.get<Preorder[]>('/api/preorder/my');
  }

}
