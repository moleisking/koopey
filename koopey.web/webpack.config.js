const webpack = require("webpack");

module.exports = {
  plugins: [
    new webpack.DefinePlugin({
      $ENV: {
        ENVIRONMENT: JSON.stringify(process.env.ENVIRONMENT),
        GOOGLE_API_KEY: JSON.stringify(process.env.GOOGLE_API_KEY),
        KOOPEY_API_URL: JSON.stringify(process.env.KOOPEY_API_URL),
      },
    }),
  ],
};
