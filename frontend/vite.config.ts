import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [react(
//      {
//    "babel":{
//      presets: ["@babel/preset-env", "@babel/preset-react"],
//      test: join(__dirname, '.'),
//    }
//  }
    )],
    server: {
        host: true,
        port: 3000,
        proxy: {
            '/api': {
                target: "http://localhost:8080",
                changeOrigin: true,
                secure: false,
            },
            '/ws': {
                target: 'ws://localhost:8080',
                ws: true,
                changeOrigin: true,
                secure:false
            },
        },
    },
    optimizeDeps: {
        esbuildOptions: {
            // Node.js global to browser globalThis
            define: {
                global: 'globalThis',
            },
        },
    },
})
