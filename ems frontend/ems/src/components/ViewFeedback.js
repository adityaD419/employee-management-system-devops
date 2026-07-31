import React, { useState, useEffect } from 'react';
import axios from 'axios';


const ViewFeedback = () => {
  const [feedbacks, setFeedbacks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  
  useEffect(() => {
    const fetchFeedbacks = async () => {
      try {
        const response = await axios.get('http://localhost:8080/admin/getfeedback');
        setFeedbacks(response.data); 
        setLoading(false);
      } catch (err) {
        setError('Failed to fetch feedbacks');
        setLoading(false);
      }
    };

    fetchFeedbacks();
  }, []);

 
  if (loading) {
    return <div className="loading-message">Loading feedbacks...</div>;
  }

  // Handle error state
  if (error) {
    return <div className="error-message">{error}</div>;
  }

  return (
   <div id='feedback-image'>
     <div className="feedback-container">
      <h2 className="mb-4">All Feedbacks</h2>

      {feedbacks.length === 0 ? (
        <p className="no-feedback-message">No feedbacks available.</p>
      ) : (
        <table className="feedback-table feedback-table-striped">
          <thead>
            <tr>
              <th>Name</th>
              <th>Email</th>
              <th>Rating</th>
              <th>Feedback</th>
            </tr>
          </thead>
          <tbody>
            {feedbacks.map((feedback) => (
              <tr key={feedback.id}>
                <td>{feedback.name}</td>
                <td>{feedback.email}</td>
                <td>{feedback.rating}</td>
                <td>{feedback.feedback}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
   </div>
  );
};

export default ViewFeedback;
