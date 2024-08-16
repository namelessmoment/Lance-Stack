import "../styles/client/clientpage.css";

export default function Fl({ name, email, skills, description }) {
  // Capitalize the first letter of the name
  const formattedName = name.charAt(0).toUpperCase() + name.slice(1);

  return (
    <div className="card">
      <div className="cardbody">
        <h4>Name: {formattedName}</h4>
        <h4>Email: {email}</h4>
        <h4>Skills: {skills}</h4>
        <p>Description: {description}</p>
      </div>
    </div>
  );
}
