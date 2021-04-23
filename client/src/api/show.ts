import request from "./request"

/**
 * Service to send and receive data from Show REST API
 */
const endpoints = {
  getAll: 'http://localhost:8080/api/shows',
};

const ShowApiService = {
  getAll(params?: any) {
    return request({
      method: "GET",
      url: endpoints.getAll,
      params,
    });
  },
}

export default ShowApiService;
