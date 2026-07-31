import React, { useState } from "react";
import axios from "axios";
import './DashBoard.css';  // Import the CSS file

const DashBoard = () => {
  const [userId, setUserId] = useState("");
  const [month, setMonth] = useState("");
  const [year, setYear] = useState("");
  const [totalSalary, setTotalSalary] = useState(null);
  const [attendanceSummary, setAttendanceSummary] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleCalculateSalary = async () => {
    setLoading(true);
    setError(null);

    try {
      const response = await axios.get("http://localhost:8080/admin/calculateTotalRating", {
        params: {
          userId,
          month,
          year,
        },
      });
      setTotalSalary(response.data);
    } catch (err) {
      setError("Error calculating salary. Please check your inputs.");
    } finally {
      setLoading(false);
    }
  };

  const handleFetchAttendanceSummary = async () => {
    setLoading(true);
    setError(null);

    try {
      const response = await axios.get(
        `http://localhost:8080/admin/summary/${userId}/${month}/${year}`
      );
      setAttendanceSummary(response.data);
    } catch (err) {
      setError("Error fetching attendance summary. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (e, setState) => {
    setState(e.target.value);
    // Reset results when new input is provided
    if (setState === setUserId) {
      setTotalSalary(null);
      setAttendanceSummary(null);
    }
  };

  return (
    <div id="dashboard-panel-details">
      <div className="dashboard-background">
        <h2>User DashBoard</h2>
        <form className="form-group">
          <label>User ID:</label>
          <input
            type="number"
            value={userId}
            onChange={(e) => handleInputChange(e, setUserId)}
            placeholder="Enter User ID"
            required
          />

          <label>Month:</label>
          <input
            type="number"
            value={month}
            onChange={(e) => handleInputChange(e, setMonth)}
            placeholder="Enter Month (1-12)"
            required
            min="1"
            max="12"
          />

          <label>Year:</label>
          <input
            type="number"
            value={year}
            onChange={(e) => handleInputChange(e, setYear)}
            placeholder="Enter Year"
            required
          />

          <div className="buttons-container">
            <button onClick={handleCalculateSalary} disabled={loading}>
              {loading ? "Loading..." : "Calculate Salary"}
            </button>
            <button onClick={handleFetchAttendanceSummary} disabled={loading}>
              {loading ? "Loading..." : "Get Attendance Summary"}
            </button>
          </div>
        </form>

        {error && <p className="error">{error}</p>}

        {totalSalary !== null && (
          <div className="result success">
            <h3>Total Salary: <span style={{ color: 'green' }}>{totalSalary}</span></h3>
          </div>
        )}

        {attendanceSummary && (
          <div className="summary-result">
            <h3>Attendance Summary for User {userId}</h3>
            <p>Full Days: {attendanceSummary.fullDays}</p>
            <p>Half Days: {attendanceSummary.halfDays}</p>
            <p>Individual Hours: {attendanceSummary.individualHours}</p>
            <p>Total Hours: {attendanceSummary.totalHours}</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default DashBoard;
