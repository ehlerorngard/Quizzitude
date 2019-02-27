import axios from "axios";
import { GraphQLClient } from 'graphql-request';

function checkStatus(response) {
  if (response.status >= 200 && response.status < 300) {
    return response;
  }
  console.log("res in checkStatus ", response);

  const error = new Error(response.statusText);
  error.response = response;
  throw error;
}

const client = new GraphQLClient('/graphql', {
  headers: {
    Authorization: 'Bearer my-jwt-token',
  }});

export default {

  getSticks: () => {
    return axios.get("/db").then(checkStatus);
  },

  getHuman: (id) => {
    console.log("getting Human in requester....");
    const query = `{
      human(id: "1003") {
        name
        id
        appearsIn
      }
    }`;

    return client.request(query).then(data => {
      console.log(data)
      return (data)
    });
  },
}