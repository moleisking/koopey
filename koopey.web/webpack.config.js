const webpack = require("webpack");

module.exports = {
  plugins: [
    new webpack.DefinePlugin({
      $ENV: {
        ENVIRONMENT: JSON.stringify(process.env.ENVIRONMENT),
        KOOPEY_SERVER_HOST: JSON.stringify(process.env.KOOPEY_SERVER_HOST),
        KOOPEY_SERVER_PORT: JSON.stringify(process.env.KOOPEY_SERVER_PORT),
        KOOPEY_SERVER_PROTOCOL: JSON.stringify(
          process.env.KOOPEY_SERVER_PROTOCOL
        ),
        KOOPEY_CLIENT_PORT: JSON.stringify(process.env.KOOPEY_CLIENT_PORT),
        GOOGLE_API_KEY: JSON.stringify(process.env.GOOGLE_API_KEY),
      },
    }),
  ],
};
