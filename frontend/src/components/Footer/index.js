import React from "react";
import { Link } from "react-router-dom";
import { Container, Row, Col } from "react-bootstrap";
import {
  FaCaretRight,
  FaMapMarkerAlt,
  FaEnvelope,
  FaPhoneAlt,
  FaFacebookF,
  FaInstagram,
  FaYoutube,
  FaTwitter,
} from "react-icons/fa";
import logo from "../../img/logo.png";

import "./style.css";

function Footer(props) {
  const onClick = (e) => {
    e.preventDefault();
  };

  return (
    <>
      <footer className="fag-footer">
        <div className="footer-top-area">
          <Container>
            <Row>
              <Col lg={4} sm={6}>
                <div className="single-footer">
                  <h3>About us</h3>
                  <p>
                    Etiam consequat sem ullamcorper, euismod metus sit amet,
                    tristique justo. Vestibulum mattis, nisi ut faucibus
                    commodo, risus ex commodo.
                  </p>
                  <p>
                    euismod metus sit amet, tristique justo. Vestibulum mattis
                    Vestibulum mattis,{" "}
                  </p>
                </div>
              </Col>
              <Col lg={2} sm={6}>
                <div className="widget-content">
                  <div className="single-footer">
                    <h3>Explore</h3>
                    <ul>
                      <li>
                        <Link to="/" onClick={onClick}>
                          <FaCaretRight />
                          About
                        </Link>
                      </li>
                      <li>
                        <Link to="/" onClick={onClick}>
                          <FaCaretRight />
                          Our Games
                        </Link>
                      </li>
                      <li>
                        <Link to="/" onClick={onClick}>
                          <FaCaretRight />
                          Contact Us
                        </Link>
                      </li>
                      <li>
                        <Link to="/" onClick={onClick}>
                          <FaCaretRight />
                          Help &amp; Support
                        </Link>
                      </li>
                      <li>
                        <Link to="/" onClick={onClick}>
                          <FaCaretRight />
                          Privacy Policy
                        </Link>
                      </li>
                    </ul>
                  </div>
                </div>
              </Col>
              <Col lg={3} sm={6}>
                <div className="widget-content">
                  <div className="single-footer">
                    <h3>Our Games</h3>
                    <ul>
                      <li>
                        <Link to="/" onClick={onClick}>
                          <FaCaretRight />
                          Need For Speed
                        </Link>
                      </li>
                      <li>
                        <Link to="/" onClick={onClick}>
                          <FaCaretRight />
                          Call Of Duty
                        </Link>
                      </li>
                      <li>
                        <Link to="/" onClick={onClick}>
                          <FaCaretRight />
                          Resident Evil
                        </Link>
                      </li>
                      <li>
                        <Link to="/" onClick={onClick}>
                          <FaCaretRight />
                          Dragons Fight
                        </Link>
                      </li>
                      <li>
                        <Link to="/" onClick={onClick}>
                          <FaCaretRight />2 Player Champions
                        </Link>
                      </li>
                    </ul>
                  </div>
                </div>
              </Col>
              <Col lg={3} sm={6}>
                <div className="single-footer">
                  <h3>Contact Us</h3>
                  <div className="footer-contact">
                    <h4 className="title">
                      <FaMapMarkerAlt />
                      Address{" "}
                    </h4>
                    <p>
                      88 road, broklyn street
                      <br />
                      new york, usa
                    </p>
                  </div>
                  <div className="footer-contact">
                    <h4 className="title">
                      <FaEnvelope />
                      Email Address
                    </h4>
                    <p>info@example.com</p>
                  </div>
                  <div className="footer-contact">
                    <h4 className="title">
                      <FaPhoneAlt />
                      Phone{" "}
                    </h4>
                    <p>777-1234-567</p>
                  </div>
                </div>
              </Col>
            </Row>
          </Container>
        </div>
        <div className="footer-bottom">
          <Container>
            <Row>
              <Col sm={12}>
                <div className="footer-bottom-inn">
                  <div className="footer-logo">
                    <a href="/">
                      <img src={logo} alt="site logo" />
                    </a>
                  </div>
                  <div className="footer-social">
                    <ul>
                      <li>
                        <Link to="/" onClick={onClick}>
                          <FaFacebookF />
                        </Link>
                      </li>
                      <li>
                        <Link to="/" onClick={onClick}>
                          <FaInstagram />
                        </Link>
                      </li>
                      <li>
                        <Link to="/" onClick={onClick}>
                          <FaYoutube />
                        </Link>
                      </li>
                      <li>
                        <Link to="/" onClick={onClick}>
                          <FaTwitter />
                        </Link>
                      </li>
                    </ul>
                  </div>
                  <div className="copyright">
                    <p>
                      &copy; Copyrights {new Date().getFullYear()} FAF - All
                      Rights Reserved
                    </p>
                  </div>
                </div>
              </Col>
            </Row>
          </Container>
        </div>
      </footer>
    </>
  );
}

export default Footer;