import React, {useEffect, useState} from "react";
import Table from 'react-bootstrap/Table';
import CardBody from "../../card/CardBody";
import {AppUser} from "../../../model/AppUser";
import UserRow from "./UserRow";
import {getUsersRequest} from "../../../service/AuthService";
import {ErrorMessage} from "../../../model/ErrorMessage";
import ErrorToast from "../../error/ErrorToast";

import './UserTable.css'


export default function UserTable() {

    const headers = ["ID", "Name", "Email", "Role"]
    const [users, setUsers] = useState<AppUser[]>([])

    const [errorMessage, setErrorMessage] = useState<ErrorMessage>({
        message: "",
        show: false
    });


    useEffect(() => {
            getUsersRequest()
                .then(
                    data => setUsers(data.data)
                ).catch(err => {
                console.log(err)
                if (err.response) {
                    setErrorMessage({
                        message: err.response.data.message,
                        show: true
                    })
                } else if (err.request) {
                    setErrorMessage({
                        message: "Something is wrong in the backend",
                        show: true
                    })
                } else {
                    setErrorMessage({
                        message: "Something is wrong in the frontend",
                        show: true
                    })
                }
            })
        }
    ,[])


    const table =
        <div id={"table-container"}>
            <Table bordered hover>
                <thead>
                <tr>
                    {headers.map((header, idx) => <th className={"text-center"} key={`${idx}-header`}>{header}</th>)}
                </tr>
                </thead>
                <tbody>
                {users.map((user, idx) => <UserRow key={`user-${idx}`} {...user}/>)}
                </tbody>
            </Table>
        </div>

    return (
        <>
            <CardBody
                title={"User List"}
                body={table}
            />
            <ErrorToast show={errorMessage.show} message={errorMessage.message} setErrorMessage={setErrorMessage}/>
        </>
    )
}