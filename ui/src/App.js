import React, { Component } from 'react';
import { Provider } from "react-redux";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import Quizzitude from "./containers/Quizzitude/Quizzitude.js";
import PageNotFound from "./PageNotFound/PageNotFound.js";
import configureStore from "./configureStore.js";
import "./App.css"
// import history from "./utils/history.js";  <–– to keep browsing history in the store via react-router-redux & history

const history = null;  // <–– default to no location history in state
const state = {};

class App extends Component {
  render() {
    return (
      <div className="App">
        <Provider store={configureStore(state, history)}>
          <BrowserRouter>
            <Switch>
              <Route exact path="/" component={Quizzitude} />
              <Route component={PageNotFound} /> 
            </Switch>
          </BrowserRouter>
        </Provider>
      </div>

    );
  }
}

export default App;
