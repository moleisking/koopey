# SAM-fe

## Install all packages dependencies (BTW after we delete node_modules folder)

npm i

## Clean (delete node_modules typings and dist folder) all folders and ReInstall all packages dependencies

npm run reinstall

## Clean (dist folder) and eliminate production files

npm run deletedist

## Serve the website for testing and development (don't forget run the back end first)

npm run serve

## Compile the project for production and set all files in dist folder

npm run build

## Serve the website with production files (port 3000)

npm run serveprod

## Troubleshoot preconfigured server example

Search for string presence
grep -rl "localhost" /usr/share/nginx/html
Chandge settings in compiled file
sudo sed -i 's/localhost/192.168.1.110/g' main.670b9311459a1bfde6b0.js.map

$ npm install angular2-google-maps --save
$ npm install @types/googlemaps --save-dev

##Complie frontend material design theme
sass deepyellow-eggshell.scss deepyellow-eggshell.css

#Colors
http://www.color-hex.com/color/eed334
mercarlista

https://material.angular.io/components/list/overview

#Security Test

<div style="background-color:lightblue">
  <h3>This is a heading</h3>
  <p>This is a paragraph.</p>
<script>
  alert( 'Hello, world!' );
</script>
</div>

# Installation

npm install -g node-gyp
make python gcc

"ngx-zxing": "^0.3.7",
"ng2-qrcode-reader": "0.0.1",
"xml-loader": "1.2.1"
"to-string-loader": "^1.1.4",
"source-map-loader": "^0.1.5",
"string-replace-loader": "^1.0.3",
"null-loader": "^0.1.1",
"raw-loader": "^0.5.1",
"file-loader": "^0.8.5",
"html-loader": "^0.4.3",
"style-loader": "^0.13.1",
"ts-loader": "^0.8.1",
"tslint-loader": "^3.5.4",
"css-loader": "^0.23.1",
"angular2-template-loader": "^0.4.0",
"awesome-typescript-loader": "^2.2.4",

https://stackoverflow.com/questions/61769665/angular-ng-serve-produces-error-ngcc-failed
