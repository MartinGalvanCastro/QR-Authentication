import React, {useState} from "react";
import {Form, Button, Row, Col} from "react-bootstrap";
import CardBody from "../card/CardBody";
import {Link, useNavigate} from "react-router-dom";
import {Register} from "../../model/AppUser";
import {signupRequest} from "../../service/AuthService";
import {ErrorMessage} from "../../model/ErrorMessage";
import ErrorToast from "../error/ErrorToast";

export default function SignUpForm() {

    const navigate = useNavigate();

    const roles = [
        "Construction Expeditor",
        "Construction Foreman",
        "Engineer",
        "Project Manager",
        "Surveyor",
    ]

    const [signUp, set_signUp] = useState<Register>({
        email: "",
        password: "",
        name: "",
        role: ""
    })

    const [errorMessage, setErrorMessage] = useState<ErrorMessage>({
        message: "",
        show: false
    });

    const handleChanges = (e: any) => {
        set_signUp({
                ...signUp,
                [e.target.name]: e.target.value
            }
        )
    }

    const handleSubmit = (e: any) => {
        e.preventDefault()
        if (signUp.email !== "" && signUp.password !== "" && signUp.name !== "" && signUp.role !=="") {
            signupRequest(signUp).then(
                data => {
                    alert("Registration succesfull");
                    navigate("/users")
                }).catch(err => {
                console.log(err)
                if (err.response) {
                    setErrorMessage({
                        message: err.response.data.message,
                        show: true
                    })
                } else if (err.request) {
                    setErrorMessage({
                        message: "Something is wrong in the backend",
                        show: true
                    })
                } else {
                    setErrorMessage({
                        message: "Something is wrong in the frontend",
                        show: true
                    })
                }
            })
        }
        console.log("Empty")
        console.log(signUp)
    }

    const form = <Form>
        <Row xs={1} md={2}>
            <Col>
                <Form.Group className="mb-3" controlId="name">
                    <Form.Label>Name</Form.Label>
                    <Form.Control required type="text" placeholder="Enter name" name="name" onChange={handleChanges}/>
                    <Form.Control.Feedback type="invalid">
                        Please provide your name
                    </Form.Control.Feedback>
                </Form.Group>
            </Col>
            <Col>
                <Form.Group className="mb-3" controlId="formBasicEmail">
                    <Form.Label>Email address</Form.Label>
                    <Form.Control required type="email" placeholder="Enter email" name="email" onChange={handleChanges}/>
                    <Form.Control.Feedback type="invalid">
                        Please provide a valid email
                    </Form.Control.Feedback>
                </Form.Group>
            </Col>
        </Row>
        <Row xs={1} md={2}>
            <Col>
                <Form.Group className="mb-3" controlId="formBasicPassword">
                    <Form.Label>Password</Form.Label>
                    <Form.Control required type="password" placeholder="Password" name="password" onChange={handleChanges}/>
                    <Form.Control.Feedback type="invalid">
                        Please provide a valid email
                    </Form.Control.Feedback>
                </Form.Group>
            </Col>
            <Col>
                <Form.Group className="mb-3">
                    <Form.Label>Role</Form.Label>
                    <Form.Control as={"select"} name={"role"}  required defaultValue={""} onChange={handleChanges}>
                        <option hidden value={""}>Select a role</option>
                        {roles.map((item, idx) => <option key={`${idx}-signup`} value={item}>{item}</option>)}
                    </Form.Control>
                </Form.Group>
            </Col>
        </Row>


        <Button variant="primary" type="submit" onClick={handleSubmit}>
            Submit
        </Button>
        <Form.Text className="text-muted ms-3">
            <Link to={"/qr"}>QR Login</Link> or <Link to={"/credentials"}>Credentials Login</Link>
        </Form.Text>
    </Form>

    return (
        <>
            <CardBody title={"Sign Up"} body={form}/>
            <ErrorToast show={errorMessage.show} message={errorMessage.message} setErrorMessage={setErrorMessage}/>
        </>
    )
}