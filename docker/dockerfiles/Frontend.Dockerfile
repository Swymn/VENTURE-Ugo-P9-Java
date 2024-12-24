# Stage 1 - the build process
FROM node:20 AS build

WORKDIR /app

COPY ./medilab/frontend/ .

RUN npm install && npm run build --prod

# Stage 2 - the run process
FROM nginx:alpine

COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html
COPY ./docker/frontend/nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]