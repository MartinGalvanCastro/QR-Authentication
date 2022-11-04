import React from "react";
import {Card} from "react-bootstrap";
import {CardBodyProp} from "../../model/CardBody";

export default function CardBody({title,body}:CardBodyProp){
    return(
        <>
            <Card.Title><h1 className={"text-center"}>{title}</h1></Card.Title>
            <Card.Body>{body}</Card.Body>
        </>
    )
}