import {User} from '../model/user';
import { CartItem } from '../model/CartItem';

export interface AuthState {
  // userData: User;
  user: User;
  token: string;
  favorites: number[];
  cartItems: CartItem[];
}

export const initialAuthState: AuthState = {
  user: null,
  token: null,
  favorites: [],
  cartItems: [],
};

export interface AppState {
  // userData: User;
  auth: AuthState;
}

export const initialState: AppState = {
  auth: initialAuthState,
};

