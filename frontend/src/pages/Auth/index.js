import React, { useState } from 'react';
import { AUTH_TYPES, DEFAULT_TIME_INTERVAL } from "utils";
import { Row, Col, Input, Button } from "nuxxxcomponentlib/dist";
import { getCurrentPageTypeLabel, createPageChangeButtons } from './helpers';
import { AuthService } from "service";

export const Auth = ({ checkPage, onError }) => {
  const [curType, setType] = useState(AUTH_TYPES.LOGIN.type);
  const [login, setLogin] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [authResponseMessage, setAuthResponseMessage] = useState("");

  const authService = new AuthService(onError);

  const onSubmit = async (event) => {
    event.preventDefault();
    switch (curType) {
      case AUTH_TYPES.LOGIN.type:
        await authService.handleLogin(email, password);
        checkPage(login);
        break;
      case AUTH_TYPES.REGISTRATION.type:
        setAuthResponseMessage(await authService.handleRegister(email, login, password));
        setTimeout(() => setAuthResponseMessage(''), DEFAULT_TIME_INTERVAL);
        break;
      case AUTH_TYPES.FORGOT_PASS.type:
        setAuthResponseMessage(await authService.handleResetPassword(email));
        setTimeout(() => setAuthResponseMessage(''), DEFAULT_TIME_INTERVAL);
        break;
    }
  };

  return (
    <div className="main-auth">
      <h2 className="main-auth__header">{getCurrentPageTypeLabel(curType)}</h2>
      <form action={curType} className="main-auth__form">
        <Row>
          <Col>
            <Input onChange={setEmail} value={email} label="Email"/>
          </Col>
        </Row>
        {
          curType !== AUTH_TYPES.FORGOT_PASS.type &&
            <>
              {curType === AUTH_TYPES.REGISTRATION.type &&
                <Row>
                  <Col>
                    <Input onChange={setLogin} value={login} label="Username"/>
                  </Col>
                </Row>
              }
              <Row>
                <Col>
                  <Input type="password" onChange={setPassword} value={password} label="Password"/>
                </Col>
              </Row>
            </>
        }
        <Row>
          <Col>
            <span className="main-auth__response">{authResponseMessage}</span>
          </Col>
        </Row>
        <Row>
          <Col>
            <Button className="main-auth__submit" onClick={onSubmit} type="submit">Submit</Button>
          </Col>
        </Row>
      </form>
      <Row curClass="main-auth__bottomButtons">
        {createPageChangeButtons(curType).map((button) => (
          <Col>
            <Button type="no-active" onClick={() => setType(button.type)}>
              {button.label}
            </Button>
          </Col>
        ))}
      </Row>
    </div>
  );
}

