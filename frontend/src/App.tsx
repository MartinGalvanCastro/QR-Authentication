import './App.css'
import React from "react";
import {Container, Card, Nav} from "react-bootstrap";
import {
    Routes,
    Route,
    Link,
} from "react-router-dom";
import NotFoundError from "./component/error/NotFoundError";
import CardBody from "./component/card/CardBody";
import LoginForm from "./component/forms/LoginForm";
import SignUpForm from "./component/forms/SignUpForm";
import UserTable from "./component/tables/users/UserTable";

const Welcome = () => {
    return (
        <CardBody
            title={"Welcome"}
            body={
                <p className={"text-center"}>To check the user list, <Link to={"/sign_up"}>Sign Up</Link>.
                    Or login with <Link to={"/qr"}>QR Code</Link> or <Link to={"/credentials"}>Credentials</Link>
                </p>
            }/>
    )
}


function App() {
    return (
        <Container className={"align-self-center d-flex justify-content-center"}>
            <Card className={"w-30"}>
                <Card.Header>
                    <Nav variant="pills" defaultActiveKey="#first">
                        <Nav.Item>
                            <Nav.Link as={Link} to="qr">Login with QR</Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link as={Link} to="credentials">Login with Credentials</Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link as={Link} to="sign_up">Sign Up</Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link as={Link} to="users">Users</Nav.Link>
                        </Nav.Item>
                    </Nav>
                </Card.Header>
                <div className={"mt-4"}>
                    <Routes>
                        <Route path={"/"} element={<Welcome/>}/>
                        <Route path={"qr"} element={<h1>QR Login</h1>}/>
                        <Route path={"credentials"} element={<LoginForm/>}/>
                        <Route path={"users"} element={<UserTable/>}/>
                        <Route path={"sign_up"} element={<SignUpForm/>}/>
                        <Route path={"*"} element={<NotFoundError/>}/>
                    </Routes>
                </div>
            </Card>
        </Container>
    )
}

export default App
