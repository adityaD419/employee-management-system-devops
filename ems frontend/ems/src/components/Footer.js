import React from 'react';
import { FaFacebook, FaGithub, FaTwitter, FaLinkedin, FaEnvelope, FaPhone } from 'react-icons/fa';

const Footer = () => {
  return (
    <footer id='footer.'>
      <div className="container">
        <div className="footer-content">
          <h3>Contact Us</h3>
          <p>
            <FaEnvelope style={{ marginRight: '8px' }} /> {/* Space between icon and text */}
            <a href="mailto:harshitabajiya2838@gmail.com" style={{ whiteSpace: 'nowrap' }}>
              harshitabajiya2838@gmail.com
            </a>
          </p>
          <p>
            <FaPhone style={{ marginRight: '8px' }} /> {/* Space between icon and text */}
            +918302001419
          </p>
        </div>
        <div className="footer-content">
          <h3>Quick Links</h3>
          <ul className="link-list">
            <li><a href="/">Home</a></li>
            <li><a href="/about">About Us</a></li>
            <li><a href="/contact">Contact Us</a></li>
          </ul>
        </div>
        <div className="footer-content">
          <h3>Social Links</h3>
          <ul className="social-icons">
            <li style={{ marginRight: '12px' }}>
              <a href="https://www.facebook.com/yourprofile" target="_blank" rel="noopener noreferrer">
                <FaFacebook />
              </a>
            </li>
            <li style={{ marginRight: '12px' }}>
              <a href="https://github.com/Harshitabajiya" target="_blank" rel="noopener noreferrer">
                <FaGithub />
              </a>
            </li>
            <li style={{ marginRight: '12px' }}>
              <a href="https://twitter.com/yourprofile" target="_blank" rel="noopener noreferrer">
                <FaTwitter />
              </a>
            </li>
            <li>
              <a href="https://in.linkedin.com/in/harshita-bajiya-720741233" target="_blank" rel="noopener noreferrer">
                <FaLinkedin />
              </a>
            </li>
          </ul>
        </div>
      </div>
      <div className="bottom-bar">
        <p>&copy; 2023 Netparam Technology, All Rights Reserved.</p>
      </div>
    </footer>
  );
};

export default Footer;
