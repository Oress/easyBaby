import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category } from '../../model/Category';
import { Product } from '../../model/Product';
import { Cart } from 'src/app/model/Cart';
import { CartItem } from 'src/app/model/CartItem';

@Injectable({
  providedIn: 'root'
})
export class ProductWebService {

  constructor(private http: HttpClient) {
  }

  public getPage(pageNum: number, pageSize: number): Observable<Product[]> {
    return this.http.get<Product[]>('/api/product', { params: { page: pageNum.toString(), size: pageSize.toString(), } });
  }

  public getMainPage(): Observable<Product[]> {
    return this.http.get<Product[]>('/api/product/main');
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

  public saveOrUpdate(prod: Product): Observable<Product> {
    return this.http.post<Product>('/api/product/saveOrUpdate', prod);
  }

  public getCart(items: CartItem[]): Observable<Cart> {
    return this.http.post<Cart>('/api/product/cart', items);
  }

  public getFavs(items: number[]): Observable<Product[]> {
    const vals = items.map(item => item.toString());
    return this.http.get<Product[]>('/api/product/favs', { params: { prodIds: vals } });
  }

  public search(query: string): Observable<Product[]> {
    return this.http.get<Product[]>('/api/product/search', { params: { query } });
  }
}
