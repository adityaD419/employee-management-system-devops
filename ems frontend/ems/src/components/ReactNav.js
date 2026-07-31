import React, { useEffect, useState, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { NavLink } from 'react-router-dom';
import './ReactNav.css'; // Import your CSS file for modal styles

function ReactNav() {
    const navigate = useNavigate();
    const codeLogo = './img/code1.jpg';
    const checkBoxRef = useRef(null);
    const navRef = useRef(null);

    const [isAdmin, setIsAdmin] = useState(false);
    const [userName, setUserName] = useState('');
    const [profileImage, setProfileImage] = useState('');
    const [showDetails, setShowDetails] = useState(false);
    const [showModal, setShowModal] = useState(false);
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    useEffect(() => {
        const loadUserData = () => {
            const adminStatus = localStorage.getItem('isAdmin') === 'true';
            const storedUserName = localStorage.getItem('userName') || 'Harshita Bajiya';
            const storedProfileImage =
                localStorage.getItem('profileImage') || 'https://via.placeholder.com/40';

            setIsAdmin(adminStatus);
            setUserName(storedUserName);
            setProfileImage(storedProfileImage);
        };

        loadUserData();

        const handleStorageChange = () => loadUserData();
        window.addEventListener('storage', handleStorageChange);

        return () => window.removeEventListener('storage', handleStorageChange);
    }, []);

    const handleNavigation = (path) => {
        if (isAdmin && path === '/dashboardoptions') {
            setShowModal(true); // Show the modal when admin tries to access the dashboard
        } else {
            navigate(path);
        }
    };

    const handleLogout = () => {
        localStorage.clear();
        setIsAdmin(false);
        navigate('/login');
    };

    const toggleDetails = () => {
        setShowDetails(!showDetails);
    };

    const handleModalSubmit = () => {
        const storedEmail = localStorage.getItem('adminEmail') || 'kajal@gmail.com';
        const storedPassword = localStorage.getItem('adminPassword') || 'Harshita@123';

        if (email === storedEmail && password === storedPassword) {
            setShowModal(false);
            navigate('/dashboardoptions');
        } else {
            setError('Invalid credentials. Please try again.');
        }
    };

    return (
        <nav ref={navRef}>
            <input type="checkbox" id="check" ref={checkBoxRef} name="" value="" />
            <label htmlFor="check" id="checkbtn">
                <i className="fa fa-bars"></i>
            </label>
            <label className="logo">
                <img className="logo-img" src={codeLogo} alt="Logo" />
            </label>

            <span className="login-container">
                {!isAdmin ? (
                    <div>
                        <a className="login-btn" id="loginBtn" onClick={() => navigate('/login')}>
                            Login
                        </a>
                        <a className="sign-btn" id="signBtn" onClick={() => navigate('/signup')}>
                            Sign Up
                        </a>
                    </div>
                ) : (
                    <div className="profile-container" onClick={toggleDetails}>
                        <img
                            className="profile-img"
                            src="/img/Car.jpg"
                            alt="Profile"
                            style={{
                                borderRadius: '60%',
                                width: '60px',
                                height: '50px',
                                cursor: 'pointer',
                            }}
                        />

                        {showDetails && (
                            <div className="profile-details">
                                <ul>
                                    <li>
                                        <strong>Name:</strong> {userName}
                                    </li>
                                    <li>
                                        <strong>Email:</strong> {localStorage.getItem('userEmail') || 'N/A'}
                                    </li>
                                    <li>
                                        <strong>Role:</strong> {isAdmin ? 'Admin' : 'User'}
                                    </li>
                                    <li className="logout" onClick={handleLogout}>
                                        Logout
                                    </li>
                                </ul>
                            </div>
                        )}
                    </div>
                )}
            </span>

            <ul>
                <li>
                    <a onClick={() => handleNavigation('/')}>Home</a>
                </li>
                <li>
                    <a onClick={() => handleNavigation('/about')}>About Us</a>
                </li>
                <li>
                    <div className="dropdown">
                        <button
                            className="btn btn-me text-light fs-4 mb-2 dropdown-toggle"
                            type="button"
                            data-bs-toggle="dropdown"
                            aria-expanded="false"
                        >
                            DashBoard
                        </button>
                        <ul className="dropdown-menu dropdown-menu-end bg-secondary p-0">
                            <li className="p-1">
                                <NavLink
                                   
                                    to="/dashboardoptions"
                                    className="dropdown-item text-white d-flex align-items-center"
                                    onClick={(e) => {
                                        e.preventDefault();
                                        handleNavigation('/dashboardoptions');
                                    }}
                                >
                                    <i className="bi bi-house me-2"></i>Admin Dashboard
                                </NavLink>
                            </li>
                            <li className="p-1">
                                <NavLink
                                    to="/dashboard"
                                    className="dropdown-item text-white d-flex align-items-center"
                                >
                                    <i className="bi bi-person-plus me-3"></i>User Dashboard
                                </NavLink>
                            </li>
                        </ul>
                    </div>
                </li>
                <li>
                    <a onClick={() => handleNavigation('/contact')}>Contact Us</a>
                </li>
                <li>
                    <a onClick={() => handleNavigation('/feedback')}>Feedback</a>
                </li>
            </ul>

            {showModal && (
                <div className="modal-overlay">
                    <div className="modal-content">
                        <h2>Admin Authentication</h2>
                        {error && <p className="error">{error}</p>}
                        <div>
                            <label>Email:</label>
                            <input
                                type="email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                            />
                        </div>
                        <div>
                            <label>Password:</label>
                            <input
                                type="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </div>
                        <button onClick={handleModalSubmit}>Submit</button>
                        <button onClick={() => setShowModal(false)}>Cancel</button>
                    </div>
                </div>
            )}
        </nav>
    );
}

export default ReactNav;
