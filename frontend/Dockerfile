FROM ubuntu:24.04

RUN apt-get update && apt-get install -y nodejs npm && \
    rm -rf /var/lib/apt/lists/*

RUN mkdir -p /app

WORKDIR /app

COPY . .

RUN npm install 

EXPOSE 9091

CMD ["npm", "run", "app-start"]