import { Link } from "react-router-dom";

export default function HomePage() {
  return (
    <div className="card" style={{ textAlign: "center" }}>
      <h1>Proyecto SPRING / REACT</h1>
      <img style={{ paddingTop: "1rem", width: "200px" }} src="../gatoprogramador.gif" alt="Gato programador" />


      <div style={{ marginTop: "2rem", display: "flex", gap: "2rem", justifyContent: "center" }}>
        <Link className="btn" to="/books">Books</Link>
        <Link className="btn" to="/clients">Clients</Link>
        <Link className="btn" to="/orders">Orders</Link>



      </div>
    </div>
  );
}
