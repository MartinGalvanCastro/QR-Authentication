import React, {useState} from "react";
import {Form, Button} from "react-bootstrap";
import CardBody from "../card/CardBody";
import {Link, useNavigate} from "react-router-dom";
import {Login} from "../../model/AppUser";
import {loginRequest} from "../../service/AuthService";
import ErrorToast from "../error/ErrorToast";
import {ErrorMessage} from "../../model/ErrorMessage";


export default function LoginForm() {

    const navigate = useNavigate();

    const [login, setLogin] = useState<Login>({
        email: "",
        password: ""
    })

    const [errorMessage,setErrorMessage] = useState<ErrorMessage>({
        message:"",
        show:false
    });



    const handleChanges = (e: any) => {
        setLogin({
                ...login,
                [e.target.name]: e.target.value
            }
        )
    }

    const handleSubmit = (e: any) => {
        e.preventDefault()
        if(login.email!=="" && login.password!==""){
            loginRequest(login).then(
                data => {
                    alert(data.data);
                    navigate("/users",{
                        replace:false
                    })
                }).catch(err=>{
                console.log(err)
                if(err.response){
                    setErrorMessage({
                        message:err.response.data.message,
                        show:true
                    })
                }else if(err.request){
                    setErrorMessage({
                        message:"Something is wrong in the backend",
                        show:true
                    })
                }else{
                    setErrorMessage({
                        message:"Something is wrong in the frontend",
                        show:true
                    })
                }
            })
        }
    }


    const form = <Form>
        <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>Email address</Form.Label>
            <Form.Control required type="email" placeholder="Enter email" name={"email"}
                          onChange={handleChanges}/>
            <Form.Control.Feedback type="invalid">
                Please provide a valid email
            </Form.Control.Feedback>
        </Form.Group>

        <Form.Group className="mb-3" controlId="formBasicPassword">
            <Form.Label>Password</Form.Label>
            <Form.Control required type="password" placeholder="Password" name={"password"} onChange={handleChanges}/>
            <Form.Control.Feedback type="invalid">
                Please provide a valid email
            </Form.Control.Feedback>
        </Form.Group>
        <Button variant="primary" type="submit" onClick={handleSubmit}>
            Submit
        </Button>
        <Form.Text className="text-muted ms-3">
            <Link to={"/sign_up"}>Or sign up here</Link>
        </Form.Text>
    </Form>

    return (
        <>
        <CardBody title={"Login In"} body={form}/>
        <ErrorToast show={errorMessage.show} message={errorMessage.message} setErrorMessage={setErrorMessage}/>
        </>
    )
}
