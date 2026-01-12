# Build Angular
FROM node:18-alpine AS build
WORKDIR /app
#package.json , package-lock.json
COPY package*.json ./
# zależności
RUN npm install
COPY . .
RUN npm run build
# Serwuj przez NGINX
FROM nginx:alpine
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /app/dist/music-management /usr/share/nginx/html
# Expose port
EXPOSE 80
# Start NGINX
CMD ["nginx", "-g", "daemon off;"]