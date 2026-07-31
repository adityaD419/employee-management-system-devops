import React from 'react';

const About = () => {
  const textStyle = {
    fontSize: "1.8em",
    color: "lavender",
    fontStyle: "italic",
  };

  return (
    <div className="aboutus-card">
      <div className="image-box">
        <img src="/img/about.avif" alt="About Us" />
        <div className="content">
          <h2 style={{ fontSize: "2.2em", color: "lavender", fontStyle: "italic" }}>
            "Welcome to our project!!"
          </h2>
          <p style={{ fontSize: "1.8em" }} >
            Our project aims to efficiently manage employee attendance and related functionalities.
            We have developed a
          </p>
          <p style={{ fontSize: "1.8em" }} >
            user-friendly interface with features like marking attendance and calculating salaries.
          </p>
        </div>
      </div>
    </div>
  );
};

export default About;
