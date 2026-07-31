import 'bootstrap/dist/css/bootstrap.min.css';

import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./components/Home";
import Login from "./components/Login";
import NoPage from "./components/NoPage";
import Footer from "./components/Footer";
import ReactNav from "./components/ReactNav";
import DashBoard from './components/DashBoard';
import About from './components/About';
import Contact from './components/Contact';
import SignUp from './components/SignUp';
import Feedback from './components/Feedback';
import Display from './components/Display';

import ViewDashBoardUser from './components/ViewDashBoardUser';
import CreateAttendance from './components/CreateAttendance';
import Company from './components/Company';
import Createrate from './components/Createrate';
import CalculateSalary from './components/CalculateSalary';
import GetAttendanceSummary from './components/GetAttendanceSummary';
import ViewMessage from './components/ViewMessage';
import ViewFeedback from './components/ViewFeedback';
import CreateEmp from './components/CreateEmp';
import DashboardOptions from './components/DashboardOptions';

// import UserDashboard from './components/UserDashBoard/UserDashBoard';



function App() {
  return (
    <>
    <BrowserRouter>
    <ReactNav />
      <Routes>
       
        <Route path="/" element={<Home />}></Route>
        <Route path="/login" element={<Login />}></Route>
        <Route path="/dashboard" element={<DashBoard />}></Route>
        <Route path="/about" element={<About />} />
        <Route path="/contact" element={<Contact />} />
        <Route path="/signup" element={<SignUp />}></Route>
        <Route path="/createemp" element={<CreateEmp />}></Route>
        <Route path="/feedback" element={<Feedback />}></Route>
        <Route path="/display" element={<Display />}></Route>
        <Route path="/dashboardoptions" element={<DashboardOptions />}></Route>
        <Route path="/viewdashboard" element={<ViewDashBoardUser />}></Route>
        <Route path="/markattendance" element={<CreateAttendance />}></Route>
        <Route path="/companystatics" element={<Company />}></Route>
        <Route path="/createrate" element={<Createrate />}></Route>
        <Route path="/calculateTotalRating" element={<CalculateSalary />}></Route>
        <Route path="/getsummary" element={<GetAttendanceSummary />}></Route>
        <Route path="/getmessage" element={<ViewMessage />}></Route>
        <Route path="/getfeedback" element={<ViewFeedback />}></Route>
        <Route path="/signup" element={<ViewFeedback />}></Route>
        <Route path="/createEmp" element={<CreateEmp />}></Route>

        <Route path="/*" element={<NoPage />}></Route>
      </Routes>
      <Footer />
    </BrowserRouter>
    </>
  );
}

export default App;
