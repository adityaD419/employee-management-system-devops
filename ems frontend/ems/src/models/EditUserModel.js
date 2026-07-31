import React, { useState, useEffect } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import axios from 'axios';

function EditUserModel(props) {
    const { userId, onHide, show } = props;
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        password: '',
        role: ''
    });
    const [errorMessage, setErrorMessage] = useState('');
    const [loading, setLoading] = useState(false);
    const [dataFetched, setDataFetched] = useState(false); // Flag to prevent redundant API calls
    const token = localStorage.getItem("token");

    useEffect(() => {
        if (userId && show) {
            setLoading(true);
            axios
                .get(`http://localhost:8080/admin/users/${userId}`, {
                    headers: { Authorization: `Bearer ${token}` },
                  })
                .then(response => {
                    setFormData(response.data);
                    setLoading(false);
                })
                .catch(error => {
                    setErrorMessage('Failed to fetch user data');
                    setLoading(false);
                    console.error('Error fetching user data', error);
                });
        }
    }, [userId, show]);

    // Reset the flag when modal is hidden
    // useEffect(() => {
    //     if (!show) {
    //         setDataFetched(false);
    //     }
    // }, [show]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        setLoading(true);
    
        axios
            .put(
                `http://localhost:8080/admin/update/${userId}`, // Endpoint URL
                formData, // Request payload (form data)
                {
                    headers: { Authorization: `Bearer ${token}` }, // Headers
                }
            )
            .then((response) => {
                alert('User updated successfully');
                setFormData({
                    name: '',
                    email: '',
                    password: '',
                    role: ''
                }); // Reset form data
                onHide(); // Hide the modal or close the form
                setLoading(false); // Reset loading state
            })
            .catch((error) => {
                setErrorMessage('Failed to update user');
                setLoading(false); // Reset loading state
                console.error('Error updating user', error);
            });
    };
    
    return (
        <Modal
            {...props}
            size="lg"
            aria-labelledby="contained-modal-title-vcenter"
            centered
        >
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">Edit User</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {errorMessage && <p className="text-danger">{errorMessage}</p>}
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
                        <label htmlFor="password" className="form-label">Password</label>
                        <input
                            type="password"
                            className="form-control"
                            id="password"
                            name="password"
                            value={formData.password}
                            onChange={handleChange}
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="role" className="form-label">Role</label>
                        <select
                            className="form-select"
                            id="role"
                            name="role"
                            value={formData.role}
                            onChange={handleChange}
                            required
                        >
                            <option value="">Select Role</option>
                            <option value="Software Developer">Software Developer</option>
                            <option value="DevOps">DevOps</option>
                            <option value="Data Associate">Data Associate</option>
                            <option value="Data Analyst">Data Analyst</option>
                            <option value="Project Manager">Project Manager</option>
                            <option value="Full Stack Developer">Full Stack Developer</option>
                            <option value="Backend Developer">Backend Developer</option>
                            <option value="Frontend Developer">Frontend Developer</option>
                        </select>
                    </div>
                    <div className="d-flex justify-content-between">
                        <Button variant="primary" type="submit" disabled={loading}>
                            {loading ? 'Updating...' : 'Submit'}
                        </Button>
                        <Button variant="secondary" onClick={onHide} disabled={loading}>
                            Cancel
                        </Button>
                    </div>
                </form>
            </Modal.Body>
        </Modal>
    );
}

export default EditUserModel;
