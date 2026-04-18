import React, { useState } from 'react';
import { Link } from "react-router-dom";

export default function OrderList({ orders = [], onAddOrder, onDeleteOrder }) {
    const [orderID, setOrderID] = useState('');
    const [clientID, setClientID] = useState('');
    const [startDate, setStartDate] = useState('');
    const [description, setDescription] = useState('');
    const [packageDimensionsCsv, setPackageDimensionsCsv] = useState('');

    async function handleSubmit(e) {
        e.preventDefault();
        await onAddOrder({
            orderID: Number(orderID),
            clientID: Number(clientID),
            startDate,
            description,
            packageDimensionsCsv,
            shopCart: []
        });
        setOrderID(''); setClientID(''); setStartDate(''); setDescription(''); setPackageDimensionsCsv('');
    }

    return (
        <div className="card">
            <div className="section-title">Orders</div>
            <form onSubmit={handleSubmit} className="form-column">
                <input placeholder="Order ID" type="number" value={orderID} onChange={e => setOrderID(e.target.value)} required />
                <input placeholder="Client ID" type="number" value={clientID} onChange={e => setClientID(e.target.value)} required />
                <input type="date" value={startDate} onChange={e => setStartDate(e.target.value)} />
                <input placeholder="Description" value={description} onChange={e => setDescription(e.target.value)} />
                <input placeholder="Package dimensions" value={packageDimensionsCsv} onChange={e => setPackageDimensionsCsv(e.target.value)} />

                <button className="btn" type="submit">Add Order</button>
            </form>

            <ul className="list">
                {orders.map(o => (
                    <li key={o.orderID} className="list-item">
                        <strong>Order #{o.orderID}</strong>
                        <div>Client: {o.clientID}</div>
                        <div>Start: {o.startDate}</div>
                        <div>{o.description}</div>
                        <button className="btn small red" onClick={() => onDeleteOrder(o.orderID)}>Delete</button>
                        <Link style={{ marginLeft: "10px" }} className="btn" to={`/edit-order/${o.orderID}`}>Edit</Link>
                    </li>
                ))}
            </ul>
        </div>
    );
}
