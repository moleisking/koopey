export const environment = {
  production: true,
  environment: $ENV.ENVIRONMENT,
  ApiKeys: {
    GoogleApiKey: $ENV.GOOGLE_API_KEY,
  },
  ClientConfigurations: {
    ClientPort: $ENV.KOOPEY_CLIENT_PORT,
  },
  ServerConfigurations: {
    ServerHost: $ENV.KOOPEY_SERVER_HOST,
    ServerPort: $ENV.KOOPEY_SERVER_PORT,
    ServerProtocol: $ENV.KOOPEY_SERVER_PROTOCOL,
  },
};
