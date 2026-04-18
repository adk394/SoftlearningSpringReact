# React frontend (independent)

This project is a minimal React frontend to integrate with the Spring Boot backend (books and clients endpoints).

Quick start (development):

```bash
cd react_frontend
npm install
npm run dev
```

The Vite dev server proxies `/api` to `http://localhost:8080` (see `vite.config.js`).

Build and run with Docker:

```bash
docker build -t react-app .
docker run -p 3000:80 react-app
```

The app expects the Spring backend to expose `/api/books` and `/api/clients` on port 8080.
