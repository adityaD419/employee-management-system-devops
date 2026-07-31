import React, { useState, useEffect } from "react";
import axios from "axios";


const Company = () => {
  const [companyStatics, setCompanyStatics] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchCompanyStatics = async () => {
      try {
        const response = await axios.get("http://localhost:8080/admin/companystatics");
        setCompanyStatics(response.data);
      } catch (err) {
        setError("Error fetching company statics. Please try again.");
        console.error(err);
      }
    };

    fetchCompanyStatics();
  }, []);

  return (
    <div className="company-container">
      <h2 className="title">Company Statics</h2>
      {error && <p className="error-message">{error}</p>}
      <div className="table-responsive">
        <table className="company-table">
          <thead>
            <tr>
              
              <th>Company Name</th>
              <th>Full Day Hours</th>
              <th>Half Day Hours</th>
            </tr>
          </thead>
          <tbody>
            {companyStatics.length > 0 ? (
              companyStatics.map((item) => (
                <tr key={item.id}>
                  
                  <td>{item.companyName}</td>
                  <td>{item.fullDay}</td>
                  <td>{item.halfDay}</td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="4" className="no-data">
                  No company statics available.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Company;
