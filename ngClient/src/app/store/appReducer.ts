import { ActionReducerMap } from '@ngrx/store';
import { Actions, EActions } from './appActions';
import { AppState, AuthState, initialAuthState } from './AppState';
/*
export const tokenReducer = createReducer(
  undefined,
  on(actions.setToken, (state, {token}) => {
    const newObj = Object.assign({}, state);
    console.log('setToken state: ', state);

    newObj.token = token;
    return newObj;
  }),
);

export const userReducer = createReducer(
  undefined,
  on(actions.setStaffData, (state, {user}) => {
    const newObj = Object.assign({}, state);

    console.log('setStaffData state: ', state);
    newObj.user = user;
    return newObj;
  }),
);*/

/*export const appReducer: ActionReducerMap<AppState> = {
  token: tokenReducer,
  user: userReducer
};*/
/*
export const appReducer = createReducer(
  initialState,
  on(actions.setToken, (state, {token}) => {
    const newObj = Object.assign({}, state);
    console.log('setToken state: ', state);

    newObj.token = token;
    return newObj;
  }),
  on(actions.setStaffData, (state, {user}) => {
    const newObj = Object.assign({}, state);

    console.log('setStaffData state: ', state);
    newObj.user = user;
    return newObj;
  }),
);

export function reducer(state: AppState | undefined, action: Action) {
  return appReducer(state, action);
}*/


export const authReducer = (
  state = initialAuthState,
  action: Actions
): AuthState => {
  switch (action.type) {
    case EActions.SetToken: {
      return {
        ...state,
        token: action.payload
      };
    }

    case EActions.SetUserData: {
      return {
        ...state,
        user: action.payload
      };
    }

    case EActions.AddToFav: {
      return {
        ...state,
        favorites: [...state.favorites, action.payload]
      };
    }

    case EActions.RemoveFromFav: {
      return {
        ...state,
        favorites: state.favorites.filter(val => val !== action.payload)
      };
    }

    case EActions.SetFavs: {
      return {
        ...state,
        favorites: action.payload
      };
    }

    case EActions.SetCartItem: {
      return {
        ...state,
        cartItems: [...state.cartItems.filter(val => val.productId !== action.prodId), { productId: action.prodId, count: action.count }]
      };
    }

    case EActions.SetCart: {
      return {
        ...state,
        cartItems: action.payload
      };
    }

    case EActions.ClearCart: {
      return {
        ...state,
        cartItems: []
      };
    }

    case EActions.RemoveFromCart: {
      return {
        ...state,
        cartItems: state.cartItems.filter(val => val.productId !== action.prodId)
      };
    }

    default:
      return state;
  }
};


export const appReducer: ActionReducerMap<AppState> = {
  auth: authReducer,
};

