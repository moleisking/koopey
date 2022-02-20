FROM nginx:1.21
LABEL version="1.0"

COPY dist /usr/share/nginx/html
COPY docker/nginx-conf /etc/nginx/conf.d/

EXPOSE 4200

WORKDIR /koopey

ENV KOOPEY_SERVER_HOST=127.0.0.1
ENV KOOPEY_SERVER_PORT=8111
ENV KOOPEY_SERVER_PROTOCOL=http
ENV GOOGLE_API_KEY=${GOOGLE_API_KEY}
RUN npm install
RUN npm install -g http-server @angular/cli@11.1.4
RUN ng build --prod



ENTRYPOINT ["http-server", "dist/koopey", "-p", "4200" ]
