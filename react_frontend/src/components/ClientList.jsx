import React, { useState } from 'react';
import { Link } from "react-router-dom";

export default function ClientList({ clients, loading, error, onAddClient, onDeleteClient }) {
  const [id, setId] = useState('');
  const [namePerson, setNamePerson] = useState('');
  const [email, setEmail] = useState('');
  const [idPerson, setIdPerson] = useState('');
  const [phone, setPhone] = useState('');
  const [adress, setAdress] = useState('');
  const [registrationDate, setRegistrationDate] = useState('');

  async function handleSubmit(e) {
    e.preventDefault();
    try {
      await onAddClient({
        id: Number(id),
        namePerson,
        email,
        idPerson,
        phone,
        adress,
        registrationDate
      });

      setId('');
      setNamePerson('');
      setEmail('');
      setIdPerson('');
      setPhone('');
      setAdress('');
      setRegistrationDate('');
    } catch (err) {
      console.error(err);
    }
  }

  return (
    <div className="card">
      <div className="section-title">Clients</div>

      <form onSubmit={handleSubmit} className="form-column">
        <input placeholder="ID" type="number" value={id} onChange={e => setId(e.target.value)} required />
        <input placeholder="Full Name" value={namePerson} onChange={e => setNamePerson(e.target.value)} required />
        <input placeholder="Email" type="email" value={email} onChange={e => setEmail(e.target.value)} required />
        <input placeholder="DNI" value={idPerson} onChange={e => setIdPerson(e.target.value)} required />
        <input placeholder="Phone Number" value={phone} onChange={e => setPhone(e.target.value)} required />
        <input placeholder="Address" value={adress} onChange={e => setAdress(e.target.value)} required />
        <input type="date" value={registrationDate} onChange={e => setRegistrationDate(e.target.value)} />

        <button className="btn" type="submit">Add Client</button>
      </form>

      <ul className="list">
        {clients.map(c => (
          <li key={c.id} className="list-item">
            <strong>{c.namePerson}</strong>
            <div>{c.idPerson}</div>
            <div>{c.email}</div>
            <div>{c.phone}</div>
            <div>{c.adress}</div>
            <div>{c.registrationDate}</div>

            <button className="btn small red" onClick={() => onDeleteClient(c.id)}>Delete</button>
            <Link style={{ marginLeft: "10px" }} className="btn" to={`/edit-client/${c.id}`}>Edit</Link>
          </li>
        ))}
      </ul>
    </div>
  );
}
