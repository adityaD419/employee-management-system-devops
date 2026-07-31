import React from 'react';
import { motion } from 'framer-motion';

const Home = () => {
  return (
    <div className="home-container">
      <motion.div
        className="home-content"
        initial={{ opacity: 0 }} // Start hidden
        whileHover={{ opacity: 1 }} // Appear when hovered
        transition={{ duration: 0.5 }} // Duration of the animation
        style={{ backgroundColor: 'rgba(230, 230, 250, 1)' }}
      >
        <h1 >Welcome to the Employee Management System</h1>
        <p>
          Manage employee attendance and salaries effortlessly.
        </p>
      </motion.div>
    </div>
  );
};

export default Home;
