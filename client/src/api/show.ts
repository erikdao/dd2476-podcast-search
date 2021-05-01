import request from "./request"

/**
 * Service to send and receive data from Show REST API
 */
const endpoints = {
  getAll: 'http://localhost:8080/api/shows',
  getById: (id: string) => `http://localhost:8080/api/shows/${id}`,
};

const ShowApiService = {
  getAll(params?: any) {
    return request({
      method: "GET",
      url: endpoints.getAll,
      params,
    });
  },
  getById(id: string, params?: any) {
    return request({
      method: "GET",
      url: endpoints.getById(id),
      params,
    });
  }, 
}

export default ShowApiService;
