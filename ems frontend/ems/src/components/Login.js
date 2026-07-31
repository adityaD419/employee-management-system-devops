import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { toast } from 'react-toastify';
const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleEmail = (e) => {
        setEmail(e.target.value);
    }
    const handlePassword = (e) => {
        setPassword(e.target.value);
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            // Making a POST request to the backend using Axios
            const response = await axios.post('http://localhost:8080/admin/loginadmin', {
                email: email,
                password: password
            });
            localStorage.setItem('token', response.data.token);
            localStorage.setItem('isAdmin', 'true');
            console.log('Login Successful:', response.data);
            toast.success("You have successfully Login!");
            localStorage.setItem("isAdmin",true);
            navigate('/dashboard');
        } catch (error) {
            localStorage.removeItem('token');
            localStorage.removeItem('id');
            console.error('Error during login:', error.response ? error.response.data : error.message);
            alert('Login failed. Please check your credentials and try again.');
        }
    };

    const handleSignUpRedirect = () => {
        navigate('/signup'); // Navigates to the Sign Up page
    };

    useEffect(()=>{
        const isAdmin = localStorage.getItem("isAdmin");
        if(isAdmin){
            navigate('/dashboard');
        }else{
            navigate('/login');
        }
    },[]);

    return (
        <div className="login-page">
            <div className="p-4 rounded bg-white shadow-sm" style={{ maxWidth: '400px', width: '100%', fontSize: '18px' }}>
                <form onSubmit={handleSubmit}>
                    <h1 className="text-center mb-4">Login</h1>
                    <div className="mb-3">
                        <label htmlFor="email"><strong>Email:</strong></label>
                        <input
                            type="email"
                            id="email"
                            autoComplete="off"
                            placeholder="Enter Email"
                            className="form-control rounded-0 border border-dark"
                            onChange={handleEmail}
                            required
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="password"><strong>Password:</strong></label>
                        <input
                            type="password"
                            id="password"
                            placeholder="Enter Password"
                            className="form-control rounded-0 border border-dark"
                            onChange={handlePassword}
                            required
                        />
                    </div>

                    <button type="submit" className="w-100 btn btn-success rounded-2" style={{ fontSize: '20px' }}>Login</button>
                    <div className="mt-3 text-center">
                        Don't have an account?
                    </div>
                    <div className="mt-2">
                        <button
                            type="button"
                            className="w-100 btn btn-primary rounded-2 text-white" style={{ fontSize: '20px' }}
                            onClick={handleSignUpRedirect} // Handle button click to navigate
                        >
                            Sign Up
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default Login;
