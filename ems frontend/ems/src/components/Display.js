import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import EditUserModel from "../models/EditUserModel";

const Display = () => {
  const [users, setUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [selectedUserId, setSelectedUserId] = useState(null);
  const [userRates, setUserRates] = useState([]);
  const [modalShow, setModalShow] = useState(false);
  const [errorMessage, setErrorMessage] = useState(null);
  const token = localStorage.getItem("token");
  const navigate = useNavigate();

  useEffect(() => {
    if (!token) {
      navigate("/login");
      return;
    }

    const fetchUsers = async () => {
      try {
        const response = await axios.get("http://localhost:8080/admin/allusers", {
          headers: { Authorization: `Bearer ${token}` },
        });
        setUsers(response.data);
      } catch (error) {
        console.error("Error fetching users:", error);
        setErrorMessage("Failed to fetch users. Please try again later.");
      }
    };

    fetchUsers();
  }, [token, navigate, modalShow]);

  const handleDelete = async (userId) => {
    if (!window.confirm("Are you sure you want to delete this user?")) return;

    try {
      await axios.delete(`http://localhost:8080/admin/delete/${userId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setUsers((prevUsers) => prevUsers.filter((user) => user.id !== userId));
    } catch (error) {
      console.error("Error deleting user:", error);
      setErrorMessage("Failed to delete user. Please try again.");
    }
  };

  const handleViewDashboard = async (userId) => {
    try {
      const response = await axios.get(`http://localhost:8080/admin/${userId}/year/2024`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setSelectedUser(users.find((user) => user.id === userId));
      setUserRates(response.data);
    } catch (error) {
      console.error("Error fetching user rates:", error);
      setErrorMessage("Failed to fetch user details. Please try again.");
    }
  };

  return (
    <div id="dashboard-image">
    <div className="dashboard-container">
      {errorMessage && <div className="error-message">{errorMessage}</div>}

      <div className="user-table-container">
      <h2 className="table-heading text-center">Display Details</h2>
        <table className="table table-responsive table-bordered">
        
          <thead>
            <tr>
              <th>ID</th>
              <th>Email</th>
              <th>Name</th>
              <th>Role</th>
              <th>Status</th>
              <th style={{ width: "150px", textAlign: "center" }}>Actions</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user) => (
              <tr key={user.id}>
                <td>{user.id}</td>
                <td>{user.email}</td>
                <td>{user.name}</td>
                <td>{user.role}</td>
                <td>{user.status}</td>
                <td>
                  <div className="table-buttons-container">
                    <button
                      className="btn btn-primary"
                      onClick={() => handleViewDashboard(user.id)}
                    >
                      View
                    </button>
                    <button
                      className="btn btn-warning"
                      onClick={() => {
                        setSelectedUserId(user.id);
                        setModalShow(true);
                      }}
                    >
                      Edit
                    </button>
                    <button
                      className="btn btn-danger"
                      onClick={() => handleDelete(user.id)}
                    >
                      Delete
                    </button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <EditUserModel
        show={modalShow}
        userId={selectedUserId}
        onHide={() => setModalShow(false)}
      />

      {selectedUser && (
        <div className="user-details-container">
          <div className="d-flex flex-column flex-md-row justify-content-between align-items-center gap-2">
            <h3 className="text-center text-md-start">{selectedUser.name}</h3>
            <button
              className="btn btn-danger"
              onClick={() => setSelectedUser(null)}
            >
              Close
            </button>
          </div>

          <p>
            <strong>ID:</strong> {selectedUser.id}
          </p>
          <p>
            <strong>Email:</strong> {selectedUser.email}
          </p>
          <p>
            <strong>Role:</strong> {selectedUser.role}
          </p>
          <h4>Monthly Rates:</h4>
          {userRates.length > 0 ? (
            <table className="table">
              <thead>
                <tr>
                  <th>Month</th>
                  <th>Rate</th>
                </tr>
              </thead>
              <tbody>
                {userRates.map((rate, index) => (
                  <tr key={index}>
                    <td>{rate.month}</td>
                    <td>{rate.rate}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          ) : (
            <p>No rates found for this year.</p>
          )}
        </div>
      )}
    </div>
    </div>
  );
};

export default Display;
