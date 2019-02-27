import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import * as serviceWorker from './serviceWorker';

import GraphiQL from 'graphiql';
import fetch from 'isomorphic-fetch';
import "../node_modules/graphiql/graphiql.css";
import "./App.css";


function graphQLFetcher(graphQLParams) {
  return fetch(window.location.origin + '/graphql', {
    method: 'post',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(graphQLParams),
  }).then(response => response.json());
}


// ReactDOM.render(<GraphiQL fetcher={graphQLFetcher} />, document.getElementById('root'));
ReactDOM.render(<App />, document.getElementById('root'));


serviceWorker.register();
