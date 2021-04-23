/**
 * Util to interact with backend REST api
 */
import axios, { AxiosRequestConfig } from 'axios';

const headers = {
    "Content-Type": "application/json",
};

const client = axios.create({ headers });

const request = async (options: AxiosRequestConfig) => {
  const onSuccess = (res: any) => res;
  const onError = (err: any) => { throw err; };

  try {
    const res = await client(options);
    return onSuccess(res);
  } catch (err) {
    return onError(err);
  }
}

export default request;
