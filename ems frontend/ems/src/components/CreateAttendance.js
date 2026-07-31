import React, { useState, useEffect } from 'react';
import axios from 'axios';
import DatePicker from 'react-datepicker';
import { toast } from 'react-toastify';

const CreateAttendance = () => {
  const [users, setUsers] = useState([]);
  const [status, setStatus] = useState('');
  const [selectedDate, setSelectedDate] = useState(new Date());
  const token = localStorage.getItem('token');

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const formattedDate = selectedDate.toISOString().split('T')[0];
        const response = await axios.get(`http://localhost:8080/admin/with-attendance?date=${formattedDate}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        setUsers(response.data.UserList);
      } catch (error) {
        console.error('Error fetching users:', error);
        setUsers([]);
      }
    };

    fetchUsers();
  }, [selectedDate, token]);

  const handleAttendanceTypeChange = (index, value) => {
    const updatedUsers = [...users];
    updatedUsers[index].attType = value;
    updatedUsers[index].hours = '';
    setUsers(updatedUsers);
  };

  const handleHoursChange = (index, value) => {
    const updatedUsers = [...users];
    updatedUsers[index].hours = value;
    updatedUsers[index].attType = '';
    setUsers(updatedUsers);
  };

  const handleSubmit = async (userId, index) => {
    const user = users[index];

    if (!user.attType && !user.hours) {
      setStatus('Please provide either an attendance type or hours.');
      return;
    }

    if (user.hours && isNaN(Number(user.hours))) {
      setStatus('Please provide a valid number for hours.');
      return;
    }

    const createdAt = selectedDate.toISOString().split('T')[0];

    const requestBody = {
      userId,
      hours: user.hours || '',
      attType: user.attType || '',
      createdAt,
    };

    try {
      const response = await fetch('http://localhost:8080/admin/mark', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(requestBody),
      });

      if (response.ok) {
        const updatedUsers = [...users];
        updatedUsers[index].attendanceMarked = true;
        setUsers(updatedUsers);
        toast.success('Attendance marked successfully!');
      } else {
        const errorData = await response.text();
        toast.error(`Error: ${errorData}`);
      }
    } catch (error) {
      console.error('Error submitting attendance:', error);
      setStatus('An error occurred while submitting attendance');
      toast.error('An error occurred while submitting attendance');
    }
  };

  return (
    <div id="atten">
      <div className="container">
        <h2 className="text-center">Attendance Table</h2>
        <div className="date-picker">
          <label>Select Date:</label>
          <DatePicker
            selected={selectedDate}
            onChange={(date) => setSelectedDate(date)}
            dateFormat="yyyy-MM-dd"
            className="form-control"
          />
        </div>
        <div className="table-responsive">
          <table className="table">
            <thead>
              <tr>
                <th>Sequence</th>
                <th>User ID</th>
                <th>Name</th>
                <th>Role</th>
                <th>Attendance Type</th>
                <th>Hours</th>
                <th>Actions</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {Array.isArray(users) && users.length > 0 ? (
                users.map((user, index) => (
                  <tr key={user.userId}>
                    <td>{index + 1}</td>
                    <td>{user.userId}</td>
                    <td>{user.userName}</td>
                    <td>{user.userRole}</td>
                    <td>
                      <select
                        className="form-control"
                        value={user.attType || ''}
                        onChange={(e) => handleAttendanceTypeChange(index, e.target.value)}
                      >
                        <option value="">Select Attendance Type</option>
                        <option value="half day">Half Day</option>
                        <option value="full day">Full Day</option>
                      </select>
                    </td>
                    <td>
                      <input
                        type="number"
                        className="form-control"
                        placeholder="Enter hours"
                        value={user.hours || ''}
                        onChange={(e) => handleHoursChange(index, e.target.value)}
                      />
                    </td>
                    <td>
                      <button
                        className="btn btn-primary"
                        onClick={() => handleSubmit(user.userId, index)}
                      >
                        Submit
                      </button>
                    </td>
                    <td>
                      {user.attendanceMarked ? (
                        <span className="text-success">Marked</span>
                      ) : (
                        <span className="text-danger">Not Marked</span>
                      )}
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="8" className="text-center">
                    No users available
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default CreateAttendance;
