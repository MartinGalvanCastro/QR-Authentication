import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import './index.css'
import {
    QueryClientProvider,
    QueryClient
} from "react-query";
import { BrowserRouter } from "react-router-dom";
import {ReactQueryDevtools} from "react-query/devtools";

const queryClient = new QueryClient()

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <QueryClientProvider client={queryClient}>
      <React.StrictMode>
          <BrowserRouter>
          <App />
          </BrowserRouter>
      </React.StrictMode>
      <ReactQueryDevtools/>
  </QueryClientProvider>
)
