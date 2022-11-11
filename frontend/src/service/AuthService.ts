import  axios from "axios";
import {Login, Register} from "../model/AppUser";
import {QRLoginMessage} from "../model/QRLoginMessage";


export const loginRequest = (payload:Login)=>{
    return axios.request({
        url:'/api/login',
        method:'POST',
        data:payload
    })
}

export const signupRequest = (payload:Register)=>{
    return axios.request({
        url:'/api/singup',
        method:'POST',
        data:payload
    })
}

export const getUsersRequest = () =>{
    return axios.request({
        url:'/api/users',
        method:'GET',
    })
}

export const postAuthToken = (payload:QRLoginMessage) =>{
    return axios.request({
        data:payload,
        url:'/api/qr_login',
        method:'POST'
    })
}

