import React, { useEffect } from 'react';
import '../style2.css'; // Import if needed
import { useNavigate } from 'react-router-dom';
import Footer from './Footer';

function DashboardOptions() {
    const navigate = useNavigate();
    const handlecreateEmpRedirect = () => {
        const isConfirm = window.confirm("Are you sure to create new employee?");
        if (isConfirm) {
            navigate('/createEmp'); 
        }
    };
    const handledisplayRedirect = () => {
        const isConfirm = window.confirm("Are you sure to display all employee?");
        if (isConfirm) {
            navigate('/display'); 
        }
    };
    const handlemarkattendanceRedirect = () => {
        const isConfirm = window.confirm("Do you want to mark attendance of employee?");
        if (isConfirm) {
            navigate('/markattendance'); 
        }
    };
    const handlecompany = () => {
        const isConfirm = window.confirm("Do you want to view company statics?");
        if (isConfirm) {
            navigate('/companystatics'); 
        }
    };
    const handlecreaterate = () => {
        const isConfirm = window.confirm("Do you want to view company statics?");
        if (isConfirm) {
            navigate('/createrate'); 
        }
    };
    const handlesalary = () => {
        const isConfirm = window.confirm("Do you want to calculate salary?");
        if (isConfirm) {
            navigate('/calculateTotalRating'); 
        }
    };
    const handlegetsummary = () => {
        const isConfirm = window.confirm("Do you want to view company statics?");
        if (isConfirm) {
            navigate('/getsummary'); 
        }
    };
    const handlegetmessage = () => {
        const isConfirm = window.confirm("Do you want to view message?");
        if (isConfirm) {
            navigate('/getmessage'); 
        }
    };
    const handlefeedback = () => {
        const isConfirm = window.confirm("Do you want to view message?");
        if (isConfirm) {
            navigate('/getfeedback'); 
        }
    };

    useEffect(()=>{
        const isAdmin = localStorage.getItem("isAdmin");
        if(isAdmin){
            navigate('/dashboardoptions');
        }else{
            navigate('/login');
        }
    },[]);
    return (

<>
        <div id='dashboardimage' className=''>

            {/* <div className="dashboard-options">
          <div className="box" onClick={() => alert('Navigate to Login')}>
                <h3> Create New User</h3>
            </div>
            <div className="box" onClick={() => alert('Navigate to Login')}>
                <h3>Display All user</h3>
            </div>
            <div className="box" onClick={() => alert('Navigate to Login')}>
                <h3>Display user by Id</h3>
            </div>
            <div className="box" onClick={() => alert('Navigate to Mark Attendance')}>
                <h3>Mark Attendance</h3>
            </div>
            <div className="box" onClick={() => alert('Navigate to Login')}>
                <h3>Update User</h3>
            </div>
            <div className="box" onClick={() => alert('Navigate to Calculate Salary')}>
                <h3>Calculate Salary</h3>
            </div>
            <div className="box" onClick={() => alert('Navigate to Login')}>
                <h3>Delete All user</h3>
            </div>
            <div className="box" onClick={() => alert('Navigate to Login')}>
                <h3>Delete user by Id</h3>
            </div>
            <div className="box" onClick={() => alert('Navigate to Login')}>
                <h3>Get Attendance Summary of User</h3>
            </div>
            <div className="box" onClick={() => alert('Navigate to Login')}>
                <h3>Create Rate</h3>
            </div>
            <div className="box" onClick={() => alert('Navigate to Login')}>
                <h3>Company Statistics</h3>
            </div>
        </div> */}
            <div className='dashboard-container'>
                <div className='row mt-3'>


                    <div className='col-12 col-md-3 mt-3 first-box '>
                        <div className='card text-light user-hover' onClick={handlecreateEmpRedirect} style={{ backgroundColor: 'rgb(18, 161, 183)', textAlign: 'center', fontWeight: 1000, cursor: 'pointer' }}>
                            <div className='card-body'>
                                <h3 className='p-4'>Create New User</h3>

                            </div>
                        </div>
                    </div>
                    <div className='col-12 col-md-3 mt-3'>
                        <div className='card text-light user-hover' onClick={handledisplayRedirect} style={{ backgroundColor: 'rgb(18, 161, 183)', textAlign: 'center', fontWeight: 1000, cursor: 'pointer' }}>
                            <div className='card-body'>
                                <h3 className='p-4'> Display All user</h3>
                            </div>
                        </div>
                    </div>
                    <div className='col-12 col-md-3 mt-3'>
                        <div className='card text-light user-hover' onClick={handlemarkattendanceRedirect} style={{ backgroundColor: 'rgb(18, 161, 183)', textAlign: 'center', fontWeight: 1000, cursor: 'pointer' }}>
                            <div className='card-body'>
                                <h3 className='p-4'>Mark Attendance</h3>
                            </div>
                        </div>
                    </div>
                        
                   
                    <div className='col-12 col-md-3 mt-3'>
                        <div className='card text-light user-hover' onClick={handlecreaterate} style={{ backgroundColor: 'rgb(18, 161, 183)', textAlign: 'center', fontWeight: 1000, cursor: 'pointer' }}>
                            <div className='card-body'>
                                <h3 className='p-4'>Create Rate</h3>
                            </div>
                        </div>
                    </div>
                  
                   
                 

                    <div className='col-12 col-md-3 mt-3'>
                        <div className='card text-light user-hover' onClick={handlesalary} style={{ backgroundColor: 'rgb(18, 161, 183)', textAlign: 'center', fontWeight: 1000, cursor: 'pointer' }}>
                            <div className='card-body'>
                                <h3 className='p-4'>Calculate Salary</h3>
                            </div>
                        </div>
                    </div>
                    <div className='col-12 col-md-3 mt-3'>
                        <div className='card text-light user-hover' onClick={(handlecompany) } style={{ backgroundColor: 'rgb(18, 161, 183)', textAlign: 'center', fontWeight: 1000, cursor: 'pointer' }}>
                            <div className='card-body'>
                                <h3 className='p-4'>Company Statistics</h3>
                            </div>
                        </div>
                    </div>

                    
                    <div className='col-12 col-md-3 mt-3'>
                        <div className='card text-light user-hover' onClick={handlegetsummary} style={{ backgroundColor: 'rgb(18, 161, 183)', textAlign: 'center', fontWeight: 1000, cursor: 'pointer' }}>
                            <div className='card-body'>
                                <h3 className='p-4'>Get Attendance Summary </h3>
                            </div>
                        </div>
                    </div>
                    <div className='col-12 col-md-3 mt-3'>
                        <div className='card text-light user-hover' onClick={handlegetmessage} style={{ backgroundColor: 'rgb(18, 161, 183)', textAlign: 'center', fontWeight: 1000, cursor: 'pointer' }}>
                            <div className='card-body'>
                                <h3 className='p-4'>View Message</h3>
                            </div>
                        </div>
                    </div>
                    <div className='col-12 col-md-3 mt-3'>
                        <div className='card text-light user-hover' onClick={handlefeedback} style={{ backgroundColor: 'rgb(18, 161, 183)', textAlign: 'center', fontWeight: 1000, cursor: 'pointer' }}>
                            <div className='card-body'>
                                <h3 className='p-4'>Feedback message</h3>
                            </div>
                        </div>
                    </div>



                </div>
            </div>
            
        </div>
     
        </>
    );
}

export default DashboardOptions;
