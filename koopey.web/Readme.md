# Koopey Web Frontend

The application consists of both a frontend and backend.

- The frontend is based on node and Angular.
- Due to CORS issues one should use the chrome browser without web security in local testing.

# Trouble Shoot

To start chrome without CORS:

> `chrome --disable-web-security --disable-gpu --user-data-dir=~/chromeTemp`

# Build frontend

To build the "frontend"

> `npm run-script build`

To build the "frontend" docker image

> `docker image build -t koopey-web-npm .`
> `docker build -t koopey-web-nginx:v1.0.0 . -f ./Dockerfile.nginx`

# Run in Windows terminal

To start the frontend which can be found in the "frontend" folder

> `npm start`

To run the "frontend" docker image, note the env variable to set the backend connectivity

> `docker container run -p 4200:4200 koopey-web-npm:v1.0.0 -e "KOOPEY_API_URL=http:\\127.0.0.1:1709" -e GOOGLE_API_KEY="XXX"`
> `docker run -d -p 4200:4200 koopey-web-nginx:v1.0.0 -e KOOPEY_API_URL="http:\\192.168.1.81:1709" -e GOOGLE_API_KEY="XXX"`
> `docker run -p 4200:80 koopey-web-nginx:v1.0.0 -e KOOPEY_API_URL="http:\\192.168.1.81:1709" -e GOOGLE_API_KEY="XXX"`

# Set environment variables

The angular environmental variables are kept in the "environments" folder. You should add a environment.dev.ts with the relevant parameters.

## Windows

### Write

> `set NODE_ENV="dev"`

#### Classic Read

> `echo %NODE_ENV%`

#### Terminal or Powershell or

> `$env:NODE_ENV`

## Linux

#### Write

> `export NODE_ENV=dev`

#### Read

> `echo $NODE_ENV`

# Links

[Koopey localhost](http://127.0.0.1:4200)
[Colors](http://www.color-hex.com/color/eed334)
[Material desighn](https://material.angular.io/components/list/overview)
[Storybook](http://127.0.0.1:6006)

# Troubleshoot

> `npm install angular2-google-maps --save`

## Complie frontend material design theme

> `sass deepyellow-eggshell.scss deepyellow-eggshell.css`

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

/_@import '~@angular/material/prebuilt-themes/deeppurple-amber.css';_/
/_@import 'deepyellow-eggshell.css';_/

/_@import "~https://fonts.googleapis.com/icon?family=Material+Icons";_/

"@angular-builders/custom-webpack": "^11.1.1",
"@angular-builders/dev-server": "~7.3.1",

# Configuration

environment.ts
Image.MaxSize is in Kilobytes

# Debug form

public findInvalidControls() {
const invalid = [];
const controls = this.formGroup.controls;
for (const name in controls) {
if (controls[name].invalid) {
invalid.push(name);
}
}
return invalid;
}

# Sonar

> `sonar-scanner.bat -D"sonar.projectKey=KoopeyWeb" -D"sonar.sources=." -D"sonar.host.url=http://localhost:9000" -D"sonar.login=312fcde051034f25d8eb3da40e7bc4c5317e479c"`

# Storybook
> `npm run storybook`

# Chrome

```java
chrome --disable-web-security  --user-data-dir=c:/temp
```
# i18n translation
> `ng extract-i18n --format=json --output-path src/localization`

## Todo
https://phrase.com/blog/posts/angular-localization-i18n/