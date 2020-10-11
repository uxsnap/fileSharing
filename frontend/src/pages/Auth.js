import React, { useState } from 'react';
import { AUTH_TYPES } from "../utils";
import { Row, Col, Input, Button, Logo } from "../components";

export const Auth = (props) => {
  const [curType, setType] = useState(AUTH_TYPES.LOGIN.type);
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");

  // const [page, setPage] = useState("Login page");

  return (
    <div className="main-auth">
      <div className="main-auth__logo"></div>
      <h2 className="main-auth__header">{
        curType === AUTH_TYPES.LOGIN.type 
          ? AUTH_TYPES.LOGIN.label
          : curType === AUTH_TYPES.REGISTRATION.type
          ? AUTH_TYPES.REGISTRATION.label
          : AUTH_TYPES.FORGOT_PASS.label
      }</h2>
      <form action={curType} className="main-auth__form" onSubmit={(e) => e.preventDefault()}>
        <Row>
          <Col>
            <Input onChange={setLogin} value={login} label="Email"/>
          </Col>
        </Row>
        <Row>
          <Col>
            <Input onChange={setPassword} value={password} label="Password"/>
          </Col>
        </Row>
        <Row>
          <Col>
            <Button type="submit">Submit</Button>
          </Col>
        </Row>
        <Row curClass="main-auth__bottomButtons">
          <Col>
            {curType !== AUTH_TYPES.REGISTRATION.type
              && <Button
                type="no-active"
                onClick={() => setType(AUTH_TYPES.REGISTRATION.type)}
              >
                {AUTH_TYPES.REGISTRATION.label}
              </Button>
            }
            {curType !== AUTH_TYPES.LOGIN.type 
              && curType !== AUTH_TYPES.FORGOT_PASS.type
              && (<Button
                type="no-active"
                onClick={() => setType(AUTH_TYPES.LOGIN.type)}
              >
                {AUTH_TYPES.LOGIN.label}
              </Button>)
            }
          </Col>
          <Col>
            {curType !== AUTH_TYPES.FORGOT_PASS.type
              && (<Button
                type="no-active"
                onClick={() => setType(AUTH_TYPES.FORGOT_PASS.type)}
              >
                {AUTH_TYPES.FORGOT_PASS.label}
              </Button>)
            }
            {curType === AUTH_TYPES.FORGOT_PASS.type
              && (<Button
                type="no-active"
                onClick={() => setType(AUTH_TYPES.LOGIN.type)}
              >
                {AUTH_TYPES.LOGIN.label}
              </Button>)
            }
          </Col>
        </Row>
      </form>
    </div>
  );
}

