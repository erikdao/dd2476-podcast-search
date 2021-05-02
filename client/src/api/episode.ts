import config from '../config';
import { TEpisodeSearchBody } from "../types";
import request from "./request";
/**
 * Service to send and receive data from Episode REST API
 */
const endpoints = {
  search: `${config.apiEndpoint}/episodes/search`,
};

const EpisodeApiService = {
  search(data: TEpisodeSearchBody) {
    return request({
      method: "POST",
      url: endpoints.search,
      data,
    });
  },
}

export default EpisodeApiService;
