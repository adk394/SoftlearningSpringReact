const API = (import.meta && import.meta.env && import.meta.env.VITE_API) || "http://localhost:8082/api";

// metodos crud

// books
export async function getBooks() {
  const res = await fetch(`${API}/books`);
  if (!res.ok) throw new Error("Error obteniendo libros");
  return res.json();
}

export async function createBook(book) {
  const res = await fetch(`${API}/books`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(book)
  });
  if (!res.ok) throw new Error("Error creando libro");
  return res.json();
}

export async function updateBook(id, book) {
  const res = await fetch(`${API}/books/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(book)
  });
  if (!res.ok) throw new Error("Error actualizando libro");
  return res.json();
}

export async function deleteBook(id) {
  const res = await fetch(`${API}/books/${id}`, {
    method: "DELETE"
  });
  if (!res.ok) throw new Error("Error eliminando libro");
  return true;
}

// clientes
export async function getClients() {
  const res = await fetch(`${API}/clients`);
  if (!res.ok) throw new Error("Error obteniendo clientes");
  return res.json();
}

export async function createClient(client) {
  const res = await fetch(`${API}/clients`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(client)
  });
  if (!res.ok) throw new Error("Error creando cliente");
  return res.json();
}

export async function updateClient(id, client) {
  const res = await fetch(`${API}/clients/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(client)
  });
  if (!res.ok) throw new Error("Error actualizando cliente");
  return res.json();
}

export async function deleteClient(id) {
  const res = await fetch(`${API}/clients/${id}`, {
    method: "DELETE"
  });
  if (!res.ok) throw new Error("Error eliminando cliente");
  return true;
}

// campaigns
export async function getPromotionCampaigns() {
  const res = await fetch(`${API}/campaigns`);
  if (!res.ok) throw new Error("Error obteniendo campañas de promoción");
  return res.json();
}

export async function createPromotionCampaign(promotionCampaign) {
  const res = await fetch(`${API}/campaigns`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(promotionCampaign)
  });
  if (!res.ok) throw new Error("Error creando campaña de promoción");
  return res.json();
}

export async function updatePromotionCampaign(id, promotionCampaign) {
  const res = await fetch(`${API}/campaigns/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(promotionCampaign)
  });
  if (!res.ok) throw new Error("Error actualizando campaña de promoción");
  return res.json();
}

export async function deletePromotionCampaign(id) {
  const res = await fetch(`${API}/campaigns/${id}`, {
    method: "DELETE"
  });
  if (!res.ok) throw new Error("Error eliminando campaña de promoción");
  return true;
}

// vehicles
export async function getVehicle() {
  const res = await fetch(`${API}/vehicles`);
  if (!res.ok) throw new Error("Error obteniendo campañas de Vehicles");
  return res.json();
}

export async function createVehicle(vehicles) {
  const res = await fetch(`${API}/vehicles`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(vehicle)
  });
  if (!res.ok) throw new Error("Error creando Vehicles");
  return res.json();
}

// orders
export async function getOrders() {
  const res = await fetch(`${API}/orders/public`);
  if (!res.ok) throw new Error("Error obteniendo orders");
  return res.json();
}

export async function getOrder(id) {
  const res = await fetch(`${API}/orders/${id}`);
  if (!res.ok) throw new Error("Error obteniendo order");
  return res.json();
}

export async function createOrder(order) {
  const res = await fetch(`${API}/orders`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(order)
  });
  if (!res.ok) throw new Error("Error creando order");
  return res.json();
}

export async function updateOrder(id, order) {
  const res = await fetch(`${API}/orders/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(order)
  });
  if (!res.ok) throw new Error("Error actualizando order");
  return res.json();
}

export async function deleteOrder(id) {
  const res = await fetch(`${API}/orders/${id}`, {
    method: "DELETE"
  });
  if (!res.ok) throw new Error("Error eliminando order");
  return true;
}

export async function updateVehicle(matricula, vehicle) {
  const res = await fetch(`${API}/vehicles/${matricula}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(vehicle)
  });
  if (!res.ok) throw new Error("Error actualizando vehicles");
  return res.json();
}

export async function deleteVehicle(matricula) {
  const res = await fetch(`${API}/vehicles/${matricula}`, {
    method: "DELETE"
  });
  if (!res.ok) throw new Error("Error eliminando vehicles");
  return true;
}
