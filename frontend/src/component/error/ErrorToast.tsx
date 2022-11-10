import React, {Dispatch, SetStateAction} from "react";
import Toast from 'react-bootstrap/Toast';
import {ToastContainer} from "react-bootstrap";
import {ErrorMessage} from "../../model/ErrorMessage";


interface IErrorToastProp extends ErrorMessage{
    setErrorMessage:Dispatch<SetStateAction<ErrorMessage>>
}

const ErrorToast = ({message, show, setErrorMessage}:IErrorToastProp)=>{
    return(
        <ToastContainer className="p-3" position={"middle-center"}>
            <Toast onClose={()=>setErrorMessage({message:"",show:false})} show={show} delay={3000} autohide>
                <Toast.Header closeButton={false}>
                    <strong className="me-auto">Something went wrong</strong>
                </Toast.Header>
                <Toast.Body>{message}</Toast.Body>
            </Toast>
        </ToastContainer>
    )
}

export default ErrorToast;