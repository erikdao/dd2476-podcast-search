/**
 * Util to interact with backend REST api
 */
import axios, { AxiosRequestConfig } from 'axios';

const headers = {
    "Content-Type": "application/json",
};

const client = axios.create({ headers });

const request = (options: AxiosRequestConfig) => {
  const onSuccess = (res: any) => res;
  const onError = (err: any) => { throw err; };

  return client(options).then(onSuccess).catch(onError);
}

export default request;
