import React, {useEffect, useState} from 'react';
import {QrReader} from 'react-qr-reader';
import CardBody from "../card/CardBody";
import {ViewFinder} from "./ViewFinder";
import {mock, useStompClient, useSubscription} from "react-stomp-hooks";
import {Button} from "react-bootstrap";
import Cookies from "js-cookie";


interface QRScannerProps {
    sessionId?: string
}

export default function QRScanner({sessionId}: QRScannerProps) {

    const dataPlaceholder = "No Data";

    //Server SessionID
    const [data, setData] = useState<string>(dataPlaceholder);


    const stompClient = useStompClient();

    //Just for checking if the message is sent to the browser
    //DEBUG Purposes
    if(sessionId){
        useSubscription(`/qr/login/${sessionId}`, (message) => {
            if (message.body) {
                const WSauthToken = JSON.parse(message.body).authToken
                Cookies.set("JSESSIONID",WSauthToken)

            }
            message.ack()
        })
    }

    const sendAuthToken = (QRsessionId:string) =>{
        console.log('Message to be pusblished')
        stompClient?.publish({
            destination: "/app/qr/login",
            body: JSON.stringify({
                authToken: "Test Token",
                WSSessionID: sessionId || QRsessionId
            }),
        })
    }

    const QRReader =
        <>
            <QrReader
                onResult={(result, error) => {
                    if (!!result) {
                        setData(result?.getText());
                        sendAuthToken(result?.getText())
                    }

                    if (!!error) {

                    }
                }}
                ViewFinder={ViewFinder}
                constraints={{facingMode: 'user'}}/>
            <p>{data}</p>
            <Button
                disabled={sessionId===undefined}
                onClick={() => {
                sendAuthToken(sessionId!!)
            }
            }> Send Browser ID</Button>
        </>

    return (
        <>
            <CardBody title={"QR Reader"} body={QRReader}/>
        </>

    );
};
