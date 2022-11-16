import React, {useState} from "react";
import CardBody from "../card/CardBody";
import {Container, Row} from "react-bootstrap";
import {WSQRCode} from "../../model/WSQRCode";
import Cookies from 'js-cookie';
import {useSubscription} from "react-stomp-hooks";

export default function QRCodeDemo({sessionID,byteArray}:WSQRCode) {


    const [authToken,setAuthToken] = useState<String>("No auth token")

    useSubscription(`/qr/login/${sessionID}`, (message) => {
        console.log('Auth Token Arrived')
        console.log('WS Message', message.body)
        console.log('WS Body',message.body)
        console.log('WS Body Exist?', message.body===undefined)
        if (message) {
            const WSauthToken = message.body
            setAuthToken(WSauthToken)
            console.log('Auth Token is:', WSauthToken)
            Cookies.set("JSESSIONID",WSauthToken,{
                sameSite: "strict",
                secure:false
            })
        }
        message.ack()
    })

    const HistoryAndInput =
        <Container>
            <Row>
                <p><strong>Client Session ID - </strong> {sessionID}</p>
            </Row>
            <Row>
                {byteArray?.length===0?
                <p>No byte array</p>:
               <img src={"data:image/png;base64,"+byteArray} alt={"QR Code"} className={"img-fluid"}/>}
            </Row>
            <Row>
                <p><strong>This is your authToken: </strong> JSESSIONID - {authToken}</p>

            </Row>
        </Container>


    return (
        <CardBody title={"QR Code Demo"} body={HistoryAndInput}/>
    )

}