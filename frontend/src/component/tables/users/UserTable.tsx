import React from "react";
import Table from 'react-bootstrap/Table';
import CardBody from "../../card/CardBody";
import {AppUser} from "../../../model/AppUser";
import UserRow from "./UserRow";


export default function UserTable(){

    const headers=["ID","Name","Email","Role"]
    const users:AppUser[] = []

    const table =
        <Table bordered hover>
            <thead>
            <tr>
                {headers.map((header,idx)=><th className={"text-center"} key={`${idx}-header`}>{header}</th>)}
            </tr>
            </thead>
            <tbody>
                {users.map((user,idx)=><UserRow key={`user-${idx}`} {...user}/>)}
            </tbody>
        </Table>

    return(
        <CardBody
            title={"User List"}
            body={table}
        />
    )
}