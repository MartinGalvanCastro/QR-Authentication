import React from "react";
import {AppUser} from "../../../model/AppUser";

export default function UserRow( {id,name,email,role}:AppUser){
    return(
        <tr>
            <td className={"text-center"}>{id}</td>
            <td className={"text-center"}>{name}</td>
            <td className={"text-center"}>{email}</td>
            <td className={"text-center"}>{role}</td>
        </tr>
    )
}
