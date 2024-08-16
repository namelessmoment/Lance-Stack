
import Login from './Pages/Login';
import { Route,Routes } from 'react-router-dom';
import Signup from './Pages/Signup';
import Home from './Pages/Home';
import ClientPage from './Pages/Client/Client';
import RegisterFreelancer from './Pages/RegisterFreelancer';
import FreelancerProfile from './Pages/Client/FreelancerProfile';
import Projects from './Pages/Client/Projects';
import ClientProfile from './Pages/Client/ClientProfile';
import Freelancer from './Pages/Freelancer/Freelancer';
import BidConfirmation from './Pages/Freelancer/BidConfirmation';
import MyProjects from './Pages/Freelancer/MyProjects';
import EditProfile from './Pages/Freelancer/EditProfile';
import ShowBids from './Pages/Freelancer/ShowBids';
import BidList from './Pages/Client/Bids';
import ContractDetails from './Pages/Client/Contract';
import PostProjectQuery from './Pages/Client/PostProject';
import CompletedProjects from './Pages/Freelancer/CompletedProjects';
import Contracts from './Pages/Freelancer/Contracts';
import UserContracts from './Pages/Client/UserContracts';
import ForgetPassword from './Pages/ForgetPassword';
import VerifyOtp from './Pages/VerifyOtp';


function App() {
  return (
    <div className="App">
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path='/loggedin' element={<h1>hello</h1>}/>
      <Route path='/signup' element={<Signup/>}/>
      <Route path='/' element={<Home/>}/>
      <Route path='/clientpage' element={<ClientPage/>}/>
      <Route path='/registerfreelancer' element={<RegisterFreelancer/>}/>
      <Route path='/FreelancerProfile' element={<FreelancerProfile/>}/>
      <Route path='/projects' element={<Projects/>}/>
      <Route path='/ClientProfile' element={<ClientProfile/>}/>
      <Route path='/bids/:projectId' element={<BidList/>} />
      <Route path="/contract/:projectId/:bidId" element={<ContractDetails />} />
      <Route path='/PostProject' element={<PostProjectQuery/>}/>
      <Route path='/user-contracts' element={<UserContracts/>}/>
      <Route path="/freelancer" element={<Freelancer />} />
        <Route path="/bid-confirmation" element={<BidConfirmation />} />
        <Route path="/my-projects" element={< MyProjects/>} />
        <Route path="/edit-profile" element={<EditProfile />} />
        <Route path="/show-bids" element={<ShowBids />} />
        <Route path="/completed-projects" element={<CompletedProjects />} /> 
        <Route path="/contracts" element={<Contracts />} />
        <Route path="/forget-password" element={<ForgetPassword/>}/>
        <Route path="/verify-otp" element={<VerifyOtp/>}/>



    </Routes>
    </div>
  );
}

export default App;
