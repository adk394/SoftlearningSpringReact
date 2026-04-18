import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { getOrder, createOrder, updateOrder } from "../services/api";
import OrderForm from "../components/OrderForm";

export default function EditOrderPage() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [order, setOrder] = useState(null);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        if (id && id !== "new") {
            setLoading(true);
            getOrder(id)
                .then((o) => setOrder(o))
                .catch((e) => alert(e.message))
                .finally(() => setLoading(false));
        } else {
            setOrder({ orderID: 0, clientID: 0, startDate: "", description: "", packageDimensionsCsv: "", shopCart: [] });
        }
    }, [id]);

    async function handleSave(payload) {
        try {
            if (id === "new") {
                await createOrder(payload);
            } else {
                await updateOrder(id, payload);
            }
            navigate("/orders");
        } catch (e) {
            alert(e.message);
        }
    }

    if (loading) return <div>Loading...</div>;

    return (
        <div style={{ padding: 20 }}>
            <h2>{id === "new" ? "Crear order" : `Editar order ${id}`}</h2>
            <OrderForm order={order} onSave={handleSave} />
        </div>
    );
}
