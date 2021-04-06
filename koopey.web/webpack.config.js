const webpack = require("webpack");

module.exports = {
  plugins: [
    new webpack.DefinePlugin({
      CUSTOM_ENVIRONMENT: {
        KOOPEY_CLIENT_PORT: JSON.stringify(process.env.KOOPEY_CLIENT_PORT),
        KOOPEY_SERVER_HOST: JSON.stringify(process.env.KOOPEY_SERVER_HOST),
        KOOPEY_SERVER_PORT: JSON.stringify(process.env.KOOPEY_SERVER_PORT),
        KOOPEY_SERVER_PROTOCOL: JSON.stringify(process.env.KOOPEY_SERVER_PROTOCOL),
      },
    }),
  ],
};