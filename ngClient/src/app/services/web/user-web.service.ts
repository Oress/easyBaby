import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Category} from '../../model/Category';
import {Product} from '../../model/Product';
import {User} from '../../model/user';

@Injectable({
  providedIn: 'root'
})
export class UserWebService {

  constructor(private http: HttpClient) {
  }

  public getByIds(ids: number[]): Observable<User[]> {
    const vals = ids.map(id => id.toString());
    return this.http.get<User[]>('/api/user', {params: {ids: vals}});
  }

  public updateData(data: User): Observable<User> {
    return this.http.post<User>('/api/user/update', data);
  }
}
