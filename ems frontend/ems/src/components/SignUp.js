import React, { useState } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';

const SignUp = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    role: '',
    showPassword: false,
  });
  const [errors, setErrors] = useState({});
  const [errorMessage, setErrorMessage] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const toggleShowPassword = () => {
    setFormData({ ...formData, showPassword: !formData.showPassword });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('http://localhost:8080/admin/newadmin', {
        name: formData.name,
        email: formData.email,
        password: formData.password,
        role: formData.role,
      });
      console.log('User Created:', response.data);
      setErrorMessage('');
      setErrors({});
      toast.success("You have successfully registered!");

      // Clear form data after successful registration
      setFormData({
        name: '',
        email: '',
        password: '',
        role: '',
        showPassword: false,
      });

    } catch (error) {
      if (error.response && error.response.status === 400) {
        setErrors(error.response.data);
      } else {
        setErrorMessage('An unexpected error occurred. Please try again later.');
      }
      console.error('Error creating user:', error);
    }
  };

  return (
    <div className="sign-page">
      <div className="container d-flex justify-content-center align-items-center min-vh-100">
        <div
          className="card p-4 shadow-sm"
          style={{ maxWidth: '400px', width: '100%', fontSize: '18px' }}
        >
          <h2 className="text-center mb-4">Sign Up</h2>
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="name" className="form-label">
                <strong>Name:</strong>
              </label>
              <input
                type="text"
                id="name"
                name="name"
                className="form-control"
                placeholder="Enter your name"
                value={formData.name}
                onChange={handleChange}
                required
              />
              {errors.name && <div className="text-danger">{errors.name}</div>}
            </div>
            <div className="mb-3">
              <label htmlFor="email" className="form-label">
                <strong>Email:</strong>
              </label>
              <input
                type="email"
                id="email"
                name="email"
                className="form-control"
                placeholder="Enter your email"
                value={formData.email}
                onChange={handleChange}
                required
              />
              {errors.email && <div className="text-danger">{errors.email}</div>}
            </div>
            <div className="mb-3">
              <label htmlFor="password" className="form-label">
                <strong>Password:</strong>
              </label>
              <div className="input-group">
                <input
                  type={formData.showPassword ? 'text' : 'password'}
                  id="password"
                  name="password"
                  className="form-control"
                  placeholder="Enter your password"
                  value={formData.password}
                  onChange={handleChange}
                  required
                />
                <button
                  type="button"
                  className="btn btn-outline-secondary"
                  onClick={toggleShowPassword}
                >
                  {formData.showPassword ? 'Hide' : 'Show'}
                </button>
              </div>
              {errors.password && <div className="text-danger">{errors.password}</div>}
            </div>
            <div className="mb-3">
              <label htmlFor="role" className="form-label">
                <strong>Role:</strong>
              </label>
              <select
                id="role"
                name="role"
                className="form-select"
                value={formData.role}
                onChange={handleChange}
                required
              >
                <option value="">Select Role</option>
                <option value="admin">Admin</option>
              </select>
              {errors.role && <div className="text-danger">{errors.role}</div>}
            </div>
            <button
              type="submit"
              className="btn btn-primary w-100"
              style={{ fontSize: '20px' }}
            >
              Sign Up
            </button>
            
            <div className="mt-3 text-center" style={{ fontSize: '18px' }}>
              <p>
                Already have an account?{' '}
                <a href="/login" className="text-decoration-none">
                  Sign In
                </a>
              </p>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default SignUp;
