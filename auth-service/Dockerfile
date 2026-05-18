FROM node:20-alpine
WORKDIR /app
COPY package*.json ./
RUN npm ci --only=production || npm install --only=production
COPY src ./src
EXPOSE 3001
USER node
CMD ["node", "src/app.js"]
