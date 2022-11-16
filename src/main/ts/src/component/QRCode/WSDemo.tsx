import React, {useEffect, useState} from "react";
import CardBody from "../card/CardBody";
import {
    useStompClient,
    useSubscription
} from "react-stomp-hooks";
import {Button, Col, Container, Form, Row} from "react-bootstrap";
import {WSMessage} from "../../model/WSMessage";


export default function WebSocketDemo() {



    const [sessionID,setSessionID] = useState("")

    const stompClient = useStompClient();
    useSubscription("/all/messages", (message) => {
        console.log(message)
        if (message.body){ setSessionID(message.body);}
        message.ack()
    });

    useEffect(() => {
        if (!stompClient?.active) {
            stompClient?.activate();
        }
    }, [])


    const sendMessage = () => {
        console.log('Message is going to be send')
        if (stompClient) {
            stompClient.publish({
                destination: "/app/hello",
                body: JSON.stringify({
                    to: "Server",
                    text: "Hi"
                })
            })
            console.log('-----> MESSAGE SENT')
        } else {
            console.log("WS Client not defined")
        }
    }


    const onSubmit = (e:any)=>{
        e.preventDefault()
        sendMessage()
    }

    const HistoryAndInput =
        <Container>
            <Row>
                <p><strong>Client Session ID - </strong> {sessionID}</p>
            </Row>
            <Row>
                <Col xs={2}>
                        <Button type="submit" onClick={onSubmit}>Get SessionID</Button>
                </Col>
            </Row>
        </Container>


    return (
        <CardBody title={"Web Socket Demo"} body={HistoryAndInput}/>
    )

}