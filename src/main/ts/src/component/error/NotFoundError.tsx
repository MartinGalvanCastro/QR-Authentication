import React from "react";
import {Link} from "react-router-dom";
import CardBody from "../card/CardBody";

export default function NotFoundError() {
    return (
        <CardBody
            title={"Page not found"}
            body=
                {
                    <p className={"text-center"}>The page you are looking for does not exist.
                        <Link to={"/"}>Click here</Link> to go back</p>
                }/>
    )
}