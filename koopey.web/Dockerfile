FROM node:14
LABEL version="1.0"

COPY /src /KoopeyWebpage/src/
COPY angular.json /KoopeyWebpage/
COPY custom-webpack.config.js /KoopeyWebpage/
COPY package.json /KoopeyWebpage/
COPY tsconfig.json /KoopeyWebpage/
COPY tsconfig.app.json /KoopeyWebpage/
COPY tslint.json /KoopeyWebpage/


WORKDIR /KoopeyWebpage

ENV KOOPEY_SERVER_HOST=127.0.0.1
ENV KOOPEY_SERVER_PORT=8111
ENV KOOPEY_SERVER_PROTOCOL=http
ENV GOOGLE_KEY=xxx
RUN npm install
RUN npm install -g http-server @angular/cli@11.1.4
RUN ng build --prod

EXPOSE 4200

ENTRYPOINT ["http-server", "dist/KoopeyWebpage", "-p", "4200" ]
