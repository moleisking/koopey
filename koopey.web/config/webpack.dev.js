var webpackMerge = require('webpack-merge');
var ExtractTextPlugin = require('extract-text-webpack-plugin');
var commonConfig = require('./webpack.common.js');
var helpers = require('./helpers');

module.exports = webpackMerge(commonConfig, {
    devtool: 'cheap-module-eval-source-map',
    debug: true,
    output: {
        path: helpers.root('dist'),
        //publicPath: 'http://localhost:3000/',
        //publicPath: 'http://192.168.1.90:3000/',
        publicPath: 'http://localhost:3000/',
       // publicPath: 'http://md-sk-ws-1:3000/',
        filename: '[name].js'
    },
    plugins: [
        new ExtractTextPlugin('[name].css')
    ],
    devServer: {
        historyApiFallback: true,
        stats: 'minimal'
    },
    tslint: {
        emitErrors: false,
        failOnHint: false,
        resourcePath: 'src'
    },
    node: {
      fs: "empty"
   }
});