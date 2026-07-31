// src/components/NotFound.js
import React from 'react';
import { Link } from 'react-router-dom';

const NoPage = () => {
  return (
    <div className="container text-center" style={{ marginTop: '100px',  marginBottom:'50px' ,padding:'100px'}}>
      <h1 className="display-3 text-danger">404</h1>
      <p className="lead" style={{fontSize:'24px'}} >Page Not Found</p>
      <p style={{fontSize:'24px'}}>The page you are looking for does not exist.</p>
      <Link to="/" className="btn btn-primary"  style={{fontSize:'20px'}}>
        Go Back to Home
      </Link>
    </div>
  );
};

export default NoPage;
