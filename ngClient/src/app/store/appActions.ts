import { Action } from '@ngrx/store';
import { User } from '../model/user';
import { CartItem } from '../model/CartItem';

export enum EActions {
  Login = '[Auth] Login',
  SetUserData = '[Auth] Set User Data',
  SetToken = '[Auth] Set token',
  AddToFav = '[Favs] Add To Favs',
  RemoveFromFav = '[Favs] Remove From Favs',
  SetFavs = '[Favs] Set Favs',
  SetCartItem = "SetCartItem",
  RemoveFromCart = "RemoveFromCart",
  SetCart = "SetCart",
  ClearCart = "ClearCart"
}

export class SetToken implements Action {
  public readonly type = EActions.SetToken;
  constructor(public payload: string) { }
}

export class SetUserData implements Action {
  public readonly type = EActions.SetUserData;
  constructor(public payload: User) { }
}

export class AddToFav implements Action {
  public readonly type = EActions.AddToFav;
  constructor(public payload: number) { }
}

export class SetFavs implements Action {
  public readonly type = EActions.SetFavs;
  constructor(public payload: number[]) { }
}

export class RemoveFromFav implements Action {
  public readonly type = EActions.RemoveFromFav;
  constructor(public payload: number) { }
}

export class SetCart implements Action {
  public readonly type = EActions.SetCart;
  constructor(public payload: CartItem[]) { }
}

export class ClearCart implements Action {
  public readonly type = EActions.ClearCart;
  constructor() { }
}

export class SetCartItem implements Action {
  public readonly type = EActions.SetCartItem;
  constructor(public prodId: number, public count: number) { }
}

export class RemoveFromCart implements Action {
  public readonly type = EActions.RemoveFromCart;
  constructor(public prodId: number) { }
}

export type Actions =
  | SetToken
  | SetUserData
  | AddToFav
  | SetFavs
  | SetCartItem
  | SetCart
  | ClearCart
  | RemoveFromCart
  | RemoveFromFav;


