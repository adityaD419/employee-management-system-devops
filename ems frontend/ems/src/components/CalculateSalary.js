import React, { useState } from "react";
import axios from "axios";


const CalculateSalary = () => {
  const [userId, setUserId] = useState("");
  const [month, setMonth] = useState("");
  const [year, setYear] = useState("");
  const [totalRating, setTotalRating] = useState(null);
  const [error, setError] = useState(null);

  const handleCalculateRating = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.get("http://localhost:8080/admin/calculateTotalRating", {
        params: {
          userId,
          month,
          year,
        },
      });
      setTotalRating(response.data);
      setError(null);
    } catch (err) {
      setTotalRating(null);
      setError("Oops!! I guess there is some issue with your inputs kindly check.");
    }
  };

  return (
    <div className="calculate-salary-image">
    <div id="calculate-salary-container">
      <h2>Calculate Total Salary/Month</h2>
      <form onSubmit={handleCalculateRating}>
        <div className="form-group">
          <label>
            User ID:
            <input
              type="number"
              value={userId}
              onChange={(e) => setUserId(e.target.value)}
              required
            />
          </label>
        </div>
        <div className="form-group">
          <label>
            Month:
            <input
              type="number"
              value={month}
              onChange={(e) => setMonth(e.target.value)}
              required
              min="1"
              max="12"
            />
          </label>
        </div>
        <div className="form-group">
          <label>
            Year:
            <input
              type="number"
              value={year}
              onChange={(e) => setYear(e.target.value)}
              required
            />
          </label>
        </div>
        <button type="submit" className="calculate-button">
          Calculate Salary
        </button>
      </form>

      {totalRating !== null && (
        <div className="result success">
          <h3>Total Salary: {totalRating}</h3>
        </div>
      )}
      {error && (
        <div className="result error">
          <h3>{error}</h3>
        </div>
      )}
    </div>
    </div>
  );
};

export default CalculateSalary;
