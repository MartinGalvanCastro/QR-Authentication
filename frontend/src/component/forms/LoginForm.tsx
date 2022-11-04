import React, {useState} from "react";
import {Form, Button} from "react-bootstrap";
import CardBody from "../card/CardBody";
import {Link} from "react-router-dom";
import {Login} from "../../model/AppUser";


export default function LoginForm() {

    const [login, setLogin] = useState<Login>({
        email:"",
        password:""
    })

    const handleChanges = (e: any) => {
        setLogin({
                ...login,
                [e.target.name]: e.target.value
            }
        )
    }

    const handleSubmit = (e:any)=>{
        alert(`Login Successful for ${login.email}`)
        console.log(login)
        e.preventDefault()
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
        <CardBody title={"Login In"} body={form}/>
    )
}