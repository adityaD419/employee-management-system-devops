import React, { useState } from 'react';
import axios from 'axios';
const Feedback = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    rating: '',
    feedback: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // Send the feedback data to the backend API
      const response = await axios.post('http://localhost:8080/admin/feedback', formData);

      // Log the response and reset the form if successful
      console.log('Feedback Submitted:', response.data);
      setFormData({ name: '', email: '', rating: '', feedback: '' });
      alert('Feedback submitted successfully!');
    } catch (error) {
      console.error('Error submitting feedback:', error);
      alert('Failed to submit feedback. Please try again.');
    }
  };

  return (
    
    <div id="createEmployee">
     <div className="container mt-5" style={{ maxWidth: '600px', backgroundColor: 'rgba(255, 255, 255, 0.8)', padding: '30px', borderRadius: '8px'}}>
      <h2 className="mb-4 "  >Feedback Form</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label htmlFor="name" className="form-label">Name</label>
          <input
            type="text"
            className="form-control"
            id="name"
            name="name"
            value={formData.name}
            onChange={handleChange}
            required
          />
        </div>

        <div className="mb-3">
          <label htmlFor="email" className="form-label">Email</label>
          <input
            type="email"
            className="form-control"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            required
          />
        </div>

        <div className="mb-3">
          <label htmlFor="rating" className="form-label">Rating (1 to 5)</label>
          <select
            className="form-select"
            id="rating"
            name="rating"
            value={formData.rating}
            onChange={handleChange}
            required
          >
            <option value="">Select Rating</option>
            <option value="1">1 - Poor</option>
            <option value="2">2 - Fair</option>
            <option value="3">3 - Good</option>
            <option value="4">4 - Very Good</option>
            <option value="5">5 - Excellent</option>
          </select>
        </div>

        <div className="mb-3">
          <label htmlFor="feedback" className="form-label" >Feedback</label>
          <textarea
            className="form-control"
            id="feedback"
            name="feedback"
            value={formData.feedback}
            onChange={handleChange}
            rows="4"
            required
          />
        </div>

        <div className="d-flex">
          <button type="submit" className="btn btn-primary me-2">Submit</button>
          <button type="button" className="btn btn-secondary" onClick={() => setFormData({ name: '', email: '', rating: '', feedback: '' })}>Clear</button>
        </div>
      </form>
    </div>
    </div>
  );
};

export default Feedback;
