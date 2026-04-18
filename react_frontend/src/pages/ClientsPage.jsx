import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getClients, createClient, deleteClient } from "../services/api";
import ClientList from "../components/ClientList";

export default function ClientsPage() {
  const [clients, setClients] = useState([]);

  useEffect(() => {
    loadClients();
  }, []);

  async function loadClients() {
    const data = await getClients();
    setClients(data);
  }

  async function handleAddClient(client) {
    const newClient = await createClient(client);
    setClients(prev => [...prev, newClient]);
  }

  async function handleDeleteClient(id) {
    await deleteClient(id);
    setClients(prev => prev.filter(c => c.id !== id));
  }

  return (
    <div className="layout">
      <ClientList
        clients={clients}
        onAddClient={handleAddClient}
        onDeleteClient={handleDeleteClient}
      />
      <div style={{ margin: "50px", justifyContent: "center", display: "flex", textAlign: "center" }}>
        <Link to="/" id="btn-2" className="btn-2">Volver a Home</Link>
      </div>
    </div>
  );
}
