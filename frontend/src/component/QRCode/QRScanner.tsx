import React, { useState } from 'react';
import { QrReader } from 'react-qr-reader';
import CardBody from "../card/CardBody";
import {ViewFinder} from "./ViewFinder";



export default function QRScanner() {
    const [data, setData] = useState('No result');

    const QRReader =
        <>
            <QrReader
                onResult={(result, error) => {
                    if (!!result) {
                        setData(result?.getText);
                    }

                    if (!!error) {
                        console.info(error);
                    }
                }}
                ViewFinder={ViewFinder}
              constraints={{ facingMode: 'user' }}/>
            <p>{data}</p>
        </>

    return(
        <>
        <CardBody title={"QR Reader"} body={QRReader}/>
        </>

    );
};
