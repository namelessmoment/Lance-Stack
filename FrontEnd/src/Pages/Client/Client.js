import "../../styles/client/clientpage.css"
import Navigation from "../../components/Navigation";

export default function ClientPage() {
  console.log(sessionStorage)
  return (
    <div className="clienthome">
    <Navigation/>
     <div className="fl">
     <br></br>
     <h3>
     Welcome back to Lancestack, your go-to platform for tech project freelancing! We’re thrilled to see you again. Here’s a quick snapshot of your recent activities:
     </h3>
     <h3>Let’s get started! Whether you're looking to kick off a new project or check on existing ones, everything you need is right at your fingertips.</h3>
     {/* <br></br>
     <h4>Active Projects: [Number of Active Projects]</h4> */}
     </div>
    </div>
  );
}
