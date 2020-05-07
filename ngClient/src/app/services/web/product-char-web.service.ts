import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Category} from '../../model/Category';
import {ProdChar} from '../../model/prodChar';

@Injectable({
  providedIn: 'root'
})
export class ProdCharWebService {

  constructor(private http: HttpClient) {
  }

  public getAll(): Observable<ProdChar[]> {
    return this.http.get<ProdChar[]>('/api/prodChar');
  }

  public getByTitleLike(str: string): Observable<ProdChar[]> {
    return this.http.get<ProdChar[]>('/api/prodChar/like', {params: {title: str}});
  }
}
