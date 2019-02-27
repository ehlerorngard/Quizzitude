import { fromJS } from 'immutable';
import { combineReducers } from 'redux-immutable';
import { LOCATION_CHANGE } from 'react-router-redux';
// import potatoReducer from "./containers/potato/potatoReducer.js";
// import leekReducer from "./containers/leek/leekReducer.js";

const routeInitialState = fromJS({
  location: null,
});

function routeReducer(state = routeInitialState, action) {
  switch (action.type) {
    case LOCATION_CHANGE:
      return state.merge({
        location: action.payload,
      });
    default:
      return state;
  }
}

export default function masterReducer() {
  console.log("masterReducer");
  return combineReducers({
    route: routeReducer,
    // potato: potatoReducer,
    // leek: leekReducer,
  });
}