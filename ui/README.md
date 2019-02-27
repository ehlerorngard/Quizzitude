# React + Redux Boilerplate

A basic boilerplate I made for my own future projects and reference;  
stunt-double API calls ready to be connected to a backend.

After playing around with a few different ways of dispatching action creators, I landed on my personally preferred way, with no mapDispatchToProps function in connect() but rather passing dispatch from props each time I call a function that needs to update state (the store):

<img src="https://user-images.githubusercontent.com/34467850/51965002-0cdd6c00-241d-11e9-8d2f-932e17e2716d.png" width="550">


... then invoking it on the action creator, also where the dispatch of an action can synchronously await an API call's response ("requester" references a file of axios-invoking functions):

<img src="https://user-images.githubusercontent.com/34467850/51965321-013e7500-241e-11e9-9e8a-7dd9f461d780.png" width="650">



## Dependencies

Foundational Node modules will need to copied from those of a create-react-app, after which
```
yarn install
```
or
```
npm i
``` 
from the project's root will install the remaining necessary dependencies.
