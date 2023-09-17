FROM node:17-alpine

RUN npm install -g nodemon

RUN npm install -g typescript

WORKDIR /app

COPY package.json .

RUN npm install

COPY . .

CMD ["npm", "run", "dev"]