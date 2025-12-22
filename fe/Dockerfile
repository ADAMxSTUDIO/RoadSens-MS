# ---- Stage 1: Build Angular app ----
FROM node:22-alpine AS build

WORKDIR /app

# Install dependencies
COPY package*.json ./
RUN npm ci

# Copy the rest of the source
COPY . .

# Build with the default configuration (uses environments/environment.ts)
RUN npm run build -- --configuration=production

# ---- Stage 2: Serve with Nginx ----
FROM nginx:alpine

# Clean default nginx static content
RUN rm -rf /usr/share/nginx/html/*

# Copy built Angular files
COPY --from=build /app/dist/fe/browser /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
