import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Category} from '../../model/Category';

@Injectable({
  providedIn: 'root'
})
export class CategoryWebService {

  constructor(private http: HttpClient) {
  }

  public getAll(): Observable<Category[]> {
    return this.http.get<Category[]>('/api/prodCategory');
  }

  public getChainByComponent(id: string): Observable<Category[]> {
    return this.http.get<Category[]>('/api/prodCategory/chainByComponent', {params: {id}});
  }

  public saveCategories(categories: Category[]): Observable<Category[]> {
    return this.http.post<Category[]>('/api/prodCategory/save', categories);
  }
}
