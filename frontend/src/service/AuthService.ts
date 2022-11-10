import  axios from "axios";
import {Login, Register} from "../model/AppUser";


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

