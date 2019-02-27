import { createStore, applyMiddleware, compose } from 'redux';
import { fromJS } from 'immutable';
import { routerMiddleware } from 'react-router-redux';
import thunkMiddleware from "redux-thunk";
import reducer from "./utils/reducer.js";

export default function configureStore(initialState = {}, history) {

  const middlewares = [
    // routerMiddleware(history),   // <–– can add react-router-redux
    thunkMiddleware,
  ];

  const enhancers = [
    applyMiddleware(...middlewares),
  ];

  const composeEnhancers =
    process.env.NODE_ENV !== 'production' &&
    typeof window === 'object' &&
    window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__
      ? window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__({
          shouldHotReload: false,
        })
      : compose;
  /* eslint-enable */

  const store = createStore(
    reducer,
    // masterReducer,  // <–– optionally we could have multiple reducer and route them through a masterReducer
    fromJS({...initialState}),
    composeEnhancers(...enhancers)
  );

  return store;
}