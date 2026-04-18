import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getOrders, createOrder, deleteOrder } from "../services/api";
import OrderList from "../components/OrderList";

export default function OrdersPage() {
    const [orders, setOrders] = useState([]);

    useEffect(() => {
        loadOrders();
    }, []);

    async function loadOrders() {
        const data = await getOrders();
        setOrders(data);
    }

    async function handleAddOrder(order) {
        const newOrder = await createOrder(order);
        setOrders(prev => [...prev, newOrder]);
    }

    async function handleDeleteOrder(id) {
        await deleteOrder(id);
        setOrders(prev => prev.filter(o => o.orderID !== id));
    }

    return (
        <div className="layout">
            <OrderList
                orders={orders}
                onAddOrder={handleAddOrder}
                onDeleteOrder={handleDeleteOrder}
            />
            <div style={{ margin: "50px", justifyContent: "center", display: "flex", textAlign: "center" }}>
                <Link to="/" id="btn-2" className="btn-2">Volver a Home</Link>
            </div>
        </div>
    );
}
