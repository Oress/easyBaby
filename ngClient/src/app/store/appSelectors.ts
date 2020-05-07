import {AppState, AuthState} from './AppState';
import {createSelector} from '@ngrx/store';
import {User} from '../model/user';
import { CartItem } from '../model/CartItem';

export const authSelector = (state: AppState) => state.auth;

export const selectToken = createSelector(
  authSelector,
  (state: AuthState) => state.token
);

export const selectUserData = createSelector(
  authSelector,
  (state: AuthState) => state.user
);

export const isStaff = createSelector(
  selectUserData,
  (state: User) => state?.isStaff
);

export const selectFavs = createSelector(
  authSelector,
  (state: AuthState) => state.favorites
);

export const isFav = (id: number) => createSelector(
  selectFavs,
  (favs: number[]) => favs.findIndex(val => val === id) !== -1
);

export const selectCart = createSelector(
  authSelector,
  (state: AuthState) => state.cartItems
);

export const selectIsInCart = (id: number) => createSelector(
  selectCart,
  (cart: CartItem[]) => cart.findIndex(val => val.productId === id) !== -1
);

export const selectCountInCart = createSelector(
  selectCart,
  (cart: CartItem[]) => cart.length
);

export const selectCountInFavs = createSelector(
  selectFavs,
  (favs: number[]) => favs ? favs.length : 0
);
