FROM node:alpine
WORKDIR /app
COPY package*.json ./
RUN npm install -g @angular/cli
RUN npm install
COPY . .
ENV PORT=4200
EXPOSE 4200
CMD ["ng", "serve", "--host", "0.0.0.0"]
