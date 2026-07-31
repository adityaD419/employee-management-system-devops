import { useState } from 'react';
import axios from 'axios';

const CreateEmp = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    role: '',
    showPassword: false,
  });
  const [errorMessage,setErrorMessage] = useState('');
  const [errors,setErrors] = useState({});

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
      const response = await axios.post('http://localhost:8080/user/newuser', formData);
      console.log('Admin Created:', response.data);
      setErrorMessage("");
      setErrors("");
    } catch (error) {
      if(error&&error.response.status==400){
        setErrors(error.response.data);
      }
      console.error('Error creating admin:', error);
      
    }
  };

  const handleClear = () => {
    setFormData({
      name: '',
      email: '',
      password: '',
      role: '',
      showPassword: false,
    });
  };

  return (
    <div id="createEmployee">
      <div className="container mt-5" style={{ maxWidth: '600px', backgroundColor: 'rgba(255, 255, 255, 0.8)', padding: '30px', borderRadius: '8px' }}>
        <h2 className="mb-4 text-center">Create new Employee</h2>
        {errorMessage&& alert(<p className='text-danger'>{errorMessage}</p>)}
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="name" className="form-label">
              Name
            </label>
            <input
              type="text"
              className="form-control"
              id="name"
              name="name"
              value={formData.name}
              onChange={handleChange}
            />
            {errors&&errors.name&&<p className='text-danger'>{errors.name}</p>}
          </div>

          <div className="mb-3">
            <label htmlFor="email" className="form-label">
              Email
            </label>
            <input
              type="email"
              className="form-control"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
            />
             {errors&&errors.email&&<p className='text-danger'>{errors.email}</p>}
          </div>

          <div className="mb-3">
            <label htmlFor="password" className="form-label">
              Password
            </label>
            <div className="input-group">
              <input
                type={formData.showPassword ? 'text' : 'password'}
                className="form-control"
                id="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
              
              />
             
              <button
                type="button"
                className="btn btn-outline-secondary"
                onClick={toggleShowPassword}
              >
                <i
                  className={formData.showPassword ? 'fas fa-eye-slash' : 'fas fa-eye'}
                ></i>
              </button>
              
            </div>
            {errors&&errors.password&&<p className='text-danger'>{errors.password}</p>}
          </div>

          <div className="mb-3">
            <label htmlFor="role" className="form-label">
              Role
            </label>
            <select
              className="form-select"
              id="role"
              name="role"
              value={formData.role}
              onChange={handleChange}
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
            <button type="submit" className="btn btn-primary">Submit</button>
            <button type="button" className="btn btn-secondary" onClick={handleClear}>Clear</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default CreateEmp;
