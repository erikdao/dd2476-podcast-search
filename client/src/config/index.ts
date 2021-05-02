export interface IConfig {
  /**
   * Root URL of the api. For example, on production environment,
   * this should be: https://podcast-search.neuraltalks.io/api
   */
  apiEndpoint?: string;
}

// Development config
const dev: IConfig = {
  apiEndpoint: 'http://localhost:8080/api',
};

// Production config
const prod: IConfig = {
  apiEndpoint: 'https://podcast-search.neuraltalks.io/api',
}

let config: IConfig = {};

console.log(process.env.REACT_APP_ENV);

if (process.env.REACT_APP_ENV == 'prod') {
  config = prod;
} else {
  config = dev;
}

export default config;
