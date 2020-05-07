import { Injectable } from '@angular/core';
import { AuthWebService } from './web/auth-web.service';
import { Store } from '@ngrx/store';
import { AppState } from '../store/AppState';
import { SetToken, SetUserData } from '../store/appActions';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import * as Cookies from 'js-cookie';
import { User } from '../model/user';
import { HttpResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private COOKIE_NAME = 'BSHOP_COOKIE';
  private jwtToken: string;
  private user: User;

  constructor(private store: Store<AppState>,
    private authWebService: AuthWebService) {
    setTimeout(() => {
      if (this.tryToGetTokenFromCookies()) {
        this.loadUserData();
      }
    }, 500);
  }

  public getToken(): string {
    return this.jwtToken;
  }

  public getUser(): User {
    return this.user;
  }

  public signin(login: string, pwd: string): Promise<any> {
    return this.authWebService.signin(login, pwd)
    .then(val => {
      console.log('setting token');
      console.log(val);
      if (val) {
        this.jwtToken = val.token;
        Cookies.set(this.COOKIE_NAME, this.jwtToken);
        console.log('Getting jwt token from login', this.jwtToken);
        this.store.dispatch(new SetToken(this.jwtToken));
        this.loadUserData();
      }
    });
  }

  public signUp(data: any): Observable<any> {
    return this.authWebService.signUp(data);
  }

  private tryToGetTokenFromCookies(): boolean {
    const value = Cookies.get(this.COOKIE_NAME);
    if (value) {
      this.jwtToken = value;
      this.store.dispatch(new SetToken(this.jwtToken));
      console.log('Token was found in cookies!');
    } else {
      console.log('Token was NOT found in cookies!');
      return false;
    }
    return true;
  }

  private loadUserData() {
    this.authWebService.me().subscribe(res => {
      console.log(res);
      this.user = res;
      this.store.dispatch(new SetUserData(res));
    });
  }
}
