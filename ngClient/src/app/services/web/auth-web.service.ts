import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthWebService {
  constructor(private http: HttpClient) {
  }

  public signin(login: string, pwd: string): Promise<any> {
    return this.http.post<any>('/api/authenticate', {username: login, password: pwd}).toPromise();
  }

  public signUp(data: any): Observable<any> {
    return this.http.post<any>('/api/signup', data);
  }

  me() {
    return this.http.get<any>('/api/user/me');
  }
}
