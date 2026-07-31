import React, { useState } from "react";
import axios from "axios";
import { toast } from "react-toastify";

const Createrate = () => {
  const [formData, setFormData] = useState({
    rate: "",
    effectiveFrom: "",
    effectiveTill: "",
    userId: "",
  });
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const validateForm = () => {
    const { effectiveFrom, effectiveTill } = formData;
    const fromDate = new Date(effectiveFrom);
    const tillDate = new Date(effectiveTill);

    if (fromDate > tillDate) {
      setError("");
      return false;
    }
    if (
      fromDate.getMonth() !== tillDate.getMonth() ||
      fromDate.getFullYear() !== tillDate.getFullYear()
    ) {
      toast.error("Effective dates must be within the same month and year.");
      // setError("Effective dates must be within the same month and year.");
      return false;
    }
    setError('');
    return true;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validateForm()) {
      // toast.error(error);
      return;
    }

    try {
      const response = await axios.post("http://localhost:8080/admin/createrate", formData);
      setMessage();
      toast.success("Rate Created Successfully!");
      setFormData({
        rate: "",
        effectiveFrom: "",
        effectiveTill: "",
        userId: "",
      });
    } catch (err) {
      
      const errorMessage = err.response?.data || "An unexpected error occurred.";
      // setError(errorMessage);

      toast.error("abc!");
      setFormData({
        rate: "",
        effectiveFrom: "",
        effectiveTill: "",
        userId: "",
      });
    }
  };

  return (
    <div id="rate-management-image">
      <div className="rate-management">
        <h2 className="rate-management__title">Rate Management</h2>

        {message && <div className="success-message">{message}</div>}
        {error && <div className="error-message">{error}</div>}

        <form className="rate-management__form" onSubmit={handleSubmit}>
          <div className="rate-management__form-group">
            <label className="rate-management__label">Rate:</label>
            <input
              className="rate-management__input"
              type="number"
              name="rate"
              value={formData.rate}
              onChange={handleInputChange}
              required
            />
          </div>
          <div className="rate-management__form-group">
            <label className="rate-management__label">Effective From:</label>
            <input
              className="rate-management__input"
              type="date"
              name="effectiveFrom"
              value={formData.effectiveFrom}
              onChange={handleInputChange}
              required
            />
          </div>
          <div className="rate-management__form-group">
            <label className="rate-management__label">Effective Till:</label>
            <input
              className="rate-management__input"
              type="date"
              name="effectiveTill"
              value={formData.effectiveTill}
              onChange={handleInputChange}
              required
            />
          </div>
          <div className="rate-management__form-group">
            <label className="rate-management__label">User ID:</label>
            <input
              className="rate-management__input"
              type="number"
              name="userId"
              value={formData.userId}
              onChange={handleInputChange}
              required
            />
          </div>
          <button type="submit" className="rate-management__submit">
            Submit
          </button>
        </form>
      </div>
    </div>
  );
};

export default Createrate;
