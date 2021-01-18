import React, { useState } from 'react';
import { AUTH_TYPES } from "utils";
import { Row, Col, Input, Button } from "components";
import { getCurrentPageTypeLabel, createPageChangeButtons } from './helpers';
import { AuthService } from "service";

export const Auth = ({ checkPage, onError }) => {
  const [curType, setType] = useState(AUTH_TYPES.LOGIN.type);
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");

  const authService = new AuthService(onError);

  const onSubmit = async (event) => {
    event.preventDefault();
    switch (curType) {
      case AUTH_TYPES.LOGIN.type:
        await authService.handleLogin(login, password);
        checkPage(login);
        break;
      case AUTH_TYPES.REGISTRATION.type:
        await authService.handleRegister(login, password);
        break;
    }
  };

  return (
    <div className="main-auth">
      <h2 className="main-auth__header">{getCurrentPageTypeLabel(curType)}</h2>
      <form action={curType} className="main-auth__form">
        <Row>
          <Col>
            <Input onChange={setLogin} value={login} label="Username"/>
          </Col>
        </Row>
        <Row>
          <Col>
            <Input type="password" onChange={setPassword} value={password} label="Password"/>
          </Col>
        </Row>
        <Row>
          <Col>
            <Button onClick={onSubmit} type="submit">Submit</Button>
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

