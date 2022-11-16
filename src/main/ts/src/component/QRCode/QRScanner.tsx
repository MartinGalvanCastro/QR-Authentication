import React, { useState} from 'react';
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
    const [authToken, setAuthToken] = useState(Cookies.get("JSESSIONID"))

    const stompClient = useStompClient();

    //Just for checking if the message is sent to the browser
    //DEBUG Purposes
    if (sessionId) {
        useSubscription(`/qr/login/${sessionId}`, (message) => {
            if (message.body) {
                const WSauthToken = JSON.parse(message.body).authToken
            }
            message.ack()
        })
    }

    const sendAuthToken = () => {
        alert('Message to be pusblished')
        const auth = Cookies.get("JSESSIONID")
        alert(`Message to be pusblished ${auth}` )
        stompClient?.publish({
            destination: "/app/qr/login",
            body: JSON.stringify({
                authToken: auth,
                WSSessionID: sessionId || data
            }),
        })
    }

    const QRReader =
        <>
            <QrReader
                onResult={(result, error) => {
                    if (!!result) {
                        if (!authToken) {
                            alert('Auth token is undefined')
                        } else {
                            setData(result?.getText());
                            sendAuthToken()
                        }

                        if (!!error) {

                        }
                    }
                }
                }
                ViewFinder={ViewFinder}
                constraints={{facingMode: 'environment'}}/>
            <p>{data}</p>
            <Button
                disabled={sessionId === undefined && data===dataPlaceholder}
                onClick={() => {
                    sendAuthToken()
                }
                }> Send Browser ID</Button>
        </>

    return (
        <>
            <CardBody title={"QR Reader"} body={QRReader}/>
        </>

    );
};
