import React, { useState } from "react";
import axios from "axios";

const GetAttendanceSummary = () => {
    const [userId, setUserId] = useState("");
    const [month, setMonth] = useState("");
    const [year, setYear] = useState("");
    const [summary, setSummary] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const fetchAttendanceSummary = async () => {
        setLoading(true);
        setError(null);

        try {
            const response = await axios.get(
                `http://localhost:8080/admin/summary/${userId}/${month}/${year}`
            );
            setSummary(response.data);
        } catch (err) {
            setError("Error fetching attendance summary. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div id="attendanceSummary">
            <div className="attendance-summary-container">
                <div className="attendance-summary">
                    <h2>Attendance Summary</h2>
                    <div className="form-group">
                        <label>User ID:</label>
                        <input
                            type="number"
                            value={userId}
                            onChange={(e) => setUserId(e.target.value)}
                            placeholder="Enter User ID"
                        />
                    </div>
                    <div className="form-group">
                        <label>Month:</label>
                        <input
                            type="number"
                            value={month}
                            onChange={(e) => setMonth(e.target.value)}
                            placeholder="Enter Month (1-12)"
                        />
                    </div>
                    <div className="form-group">
                        <label>Year:</label>
                        <input
                            type="number"
                            value={year}
                            onChange={(e) => setYear(e.target.value)}
                            placeholder="Enter Year"
                        />
                    </div>
                    <button onClick={fetchAttendanceSummary} disabled={loading}>
                        {loading ? "Loading..." : "Get Summary"}
                    </button>

                    {error && <p className="error">{error}</p>}
                    {summary && (
                        <div className="summary-result">
                            <h3>Attendance Summary for User {userId}</h3>
                            <p>Full Days: {summary.fullDays}</p>
                            <p>Half Days: {summary.halfDays}</p>
                            <p>Individual Hours: {summary.individualHours}</p>
                            <p>Total Hours: {summary.totalHours}</p>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default GetAttendanceSummary;
