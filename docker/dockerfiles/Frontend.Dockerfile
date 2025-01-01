# Stage 1 - the build process
FROM node:20 AS build

WORKDIR /app

COPY ./medilab/frontend/ .

ENV API_URL=http://localhost:8080

RUN npm install && npm run build --prod -- --configuration=production

# Stage 2 - the run process
FROM nginx:alpine

COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html
COPY ./docker/frontend/nginx.conf /etc/nginx/conf.d/default.conf
COPY ./docker/frontend/entrypoint.sh /docker-entrypoint.d/entrypoint.sh

RUN chmod +x /docker-entrypoint.d/entrypoint.sh

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]