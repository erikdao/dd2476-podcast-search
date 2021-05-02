import request from "./request"
import config from '../config';

/**
 * Service to send and receive data from Show REST API
 */
const endpoints = {
  getAll: `${config.apiEndpoint}/shows`,
  getById: (id: string) => `${config.apiEndpoint}/shows/${id}`,
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
