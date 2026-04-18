import { Routes, Route } from "react-router-dom";

import HomePage from "./pages/HomePage";
import BooksPage from "./pages/BooksPage";
import ClientsPage from "./pages/ClientsPage";
import EditBookPage from "./pages/EditBookPage";
import EditClientPage from "./pages/EditClientPage";
import Header from "./components/Header";
import OrdersPage from "./pages/OrdersPage";
import EditOrderPage from "./pages/EditOrderPage";



export default function App() {
  return (
    <>
      <Header />
      <Routes>
        <Route path="/" element={<HomePage />} />

        <Route path="/books" element={<BooksPage />} />
        <Route path="/edit-book/:id" element={<EditBookPage />} />

        <Route path="/clients" element={<ClientsPage />} />
        <Route path="/edit-client/:id" element={<EditClientPage />} />

        <Route path="/orders" element={<OrdersPage />} />
        <Route path="/edit-order/:id" element={<EditOrderPage />} />
      </Routes>
    </>
  );
}
