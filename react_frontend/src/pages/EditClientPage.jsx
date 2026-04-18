import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getClients, updateClient } from "../services/api";

export default function EditClientPage() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [client, setClient] = useState(null);

  useEffect(() => {
    loadClient();
  }, []);

  async function loadClient() {
    const all = await getClients();
    const found = all.find(c => c.id === Number(id));
    setClient(found);
  }

  async function handleSubmit(e) {
    e.preventDefault();
    await updateClient(id, client);
    navigate("/");
  }

  if (!client) return <div>Cargando...</div>;

  return (
    <div className="card">
      <h2>Edit Client</h2>

      <form onSubmit={handleSubmit} className="form-column">
        <input value={client.namePerson} onChange={e => setClient({ ...client, namePerson: e.target.value })} />
        <input value={client.email} onChange={e => setClient({ ...client, email: e.target.value })} />
        <input value={client.idPerson} onChange={e => setClient({ ...client, idPerson: e.target.value })} />
        <input value={client.phone} onChange={e => setClient({ ...client, phone: e.target.value })} />
        <input value={client.adress} onChange={e => setClient({ ...client, adress: e.target.value })} />
        <input type="date" value={client.registrationDate || ""} onChange={e => setClient({ ...client, registrationDate: e.target.value })} />

        <button className="btn">Save</button>
      </form>
    </div>
  );
}
