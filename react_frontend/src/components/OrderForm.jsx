import { useState, useEffect } from "react";

export default function OrderForm({ order, onSave }) {
    const [form, setForm] = useState({ orderID: 0, clientID: 0, startDate: "", description: "", packageDimensionsCsv: "", shopCart: [] });

    useEffect(() => {
        if (order) {
            // asegurar shopcart y convertir campos basicos
            const shopCart = (order.shopCart || []).map(d => ({ id: d.id || 0, ref: d.ref || "", price: d.price || 0, discount: d.discount || 0, amount: d.amount || 1 }));
            setForm({
                orderID: order.orderID || 0,
                clientID: order.clientID || 0,
                startDate: order.startDate || "",
                description: order.description || "",
                packageDimensionsCsv: order.packageDimensionsCsv || "",
                shopCart
            });
        }
    }, [order]);

    function setField(field, value) {
        setForm((s) => ({ ...s, [field]: value }));
    }

    function addDetail() {
        setForm((s) => ({ ...s, shopCart: [...(s.shopCart || []), { id: 0, ref: "", price: 0, discount: 0, amount: 1 }] }));
    }

    function updateDetail(idx, key, value) {
        const next = [...(form.shopCart || [])];
        next[idx] = { ...next[idx], [key]: value };
        setForm((s) => ({ ...s, shopCart: next }));
    }

    function removeDetail(idx) {
        const next = [...(form.shopCart || [])];
        next.splice(idx, 1);
        setForm((s) => ({ ...s, shopCart: next }));
    }

    function submit(e) {
        e.preventDefault();
        // normalizar campos numericos
        const normalized = { ...form };
        normalized.orderID = Number(normalized.orderID || 0);
        normalized.clientID = Number(normalized.clientID || 0);
        if (normalized.shopCart) {
            normalized.shopCart = normalized.shopCart.map((d) => ({ id: Number(d.id || 0), ref: d.ref, price: Number(d.price || 0), discount: Number(d.discount || 0), amount: Number(d.amount || 0) }));
        }
        onSave(normalized);
    }

    return (
        <form onSubmit={submit}>
            <div>
                <label>Order ID: </label>
                <input type="number" value={form.orderID} onChange={(e) => setField("orderID", Number(e.target.value))} />
            </div>
            <div>
                <label>Client ID: </label>
                <input type="number" value={form.clientID} onChange={(e) => setField("clientID", Number(e.target.value))} />
            </div>
            <div>
                <label>Start Date: </label>
                <input value={form.startDate} onChange={(e) => setField("startDate", e.target.value)} />
            </div>
            <div>
                <label>Description: </label>
                <input value={form.description} onChange={(e) => setField("description", e.target.value)} />
            </div>
            <div>
                <label>Package Dimensions: </label>
                <input value={form.packageDimensionsCsv} onChange={(e) => setField("packageDimensionsCsv", e.target.value)} />
            </div>

            <div style={{ marginTop: 12 }}>
                <h4>Shop Cart</h4>
                <button type="button" onClick={addDetail}>Add item</button>
                {(form.shopCart || []).map((d, idx) => (
                    <div key={idx} style={{ border: "1px solid #ddd", padding: 8, marginTop: 8 }}>
                        <div>
                            <label>Ref: </label>
                            <input value={d.ref} onChange={(e) => updateDetail(idx, "ref", e.target.value)} />
                        </div>
                        <div>
                            <label>Price: </label>
                            <input type="number" value={d.price} onChange={(e) => updateDetail(idx, "price", e.target.value)} />
                        </div>
                        <div>
                            <label>Discount: </label>
                            <input type="number" value={d.discount} onChange={(e) => updateDetail(idx, "discount", e.target.value)} />
                        </div>
                        <div>
                            <label>Amount: </label>
                            <input type="number" value={d.amount} onChange={(e) => updateDetail(idx, "amount", e.target.value)} />
                        </div>
                        <div>
                            <button type="button" onClick={() => removeDetail(idx)}>Remove</button>
                        </div>
                    </div>
                ))}
            </div>

            <div style={{ marginTop: 12 }}>
                <button type="submit">Save</button>
            </div>
        </form>
    );
}
