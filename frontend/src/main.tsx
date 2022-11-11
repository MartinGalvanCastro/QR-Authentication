import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import './index.css'
import {
    QueryClientProvider,
    QueryClient
} from "react-query";
import {BrowserRouter} from "react-router-dom";
import {ReactQueryDevtools} from "react-query/devtools";
import {StompSessionProvider} from "react-stomp-hooks";


const queryClient = new QueryClient()

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
    <QueryClientProvider client={queryClient}>
        <React.StrictMode>
            <BrowserRouter>
                <StompSessionProvider
                    url={"/ws"}
                    debug={(str) => {
                        console.log(str);
                    }}
                    connectionTimeout={10*1000}
                    onStompError={(err:any) => console.log(err)}
                    onWebSocketError={(err:any) => console.log(err)}>
                    <App/>
                </StompSessionProvider>
            </BrowserRouter>
        </React.StrictMode>
        <ReactQueryDevtools/>
    </QueryClientProvider>
)
