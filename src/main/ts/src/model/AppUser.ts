export interface Login {
    email:string,
    password:string
}

export interface Register extends Login{
    name:string,
    role:string
}

export interface AppUser{
    id:string
    name:string,
    email:string,
    role:string
}