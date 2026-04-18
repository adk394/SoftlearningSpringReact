import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getBooks, updateBook } from "../services/api";

export default function EditBookPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [book, setBook] = useState(null);

  useEffect(() => {
    loadBook();
  }, []);

  async function loadBook() {
    const all = await getBooks();
    const found = all.find(b => b.id === Number(id));
    setBook(found);
  }

  async function handleSubmit(e) {
    e.preventDefault();
    await updateBook(id, book);
    navigate("/");
  }

  if (!book) return <div>Cargando...</div>;

  return (
    <div className="card">
      <h2>Edit Book</h2>

      <form onSubmit={handleSubmit} className="form-column">
        <input value={book.title} onChange={e => setBook({ ...book, title: e.target.value })} />
        <input value={book.author} onChange={e => setBook({ ...book, author: e.target.value })} />
        <input type="number" value={book.price} onChange={e => setBook({ ...book, price: parseFloat(e.target.value) })} />
        <input value={book.isbn} onChange={e => setBook({ ...book, isbn: e.target.value })} />
        <input value={book.publisher} onChange={e => setBook({ ...book, publisher: e.target.value })} />
        <input type="date" value={book.releaseDate || ""} onChange={e => setBook({ ...book, releaseDate: e.target.value })} />

        <input type="number" value={book.weight} onChange={e => setBook({ ...book, weight: parseFloat(e.target.value) })} />
        <input type="number" value={book.height} onChange={e => setBook({ ...book, height: parseFloat(e.target.value) })} />
        <input type="number" value={book.width} onChange={e => setBook({ ...book, width: parseFloat(e.target.value) })} />
        <input type="number" value={book.depth} onChange={e => setBook({ ...book, depth: parseFloat(e.target.value) })} />

        <button className="btn">Save</button>
      </form>
    </div>
  );
}
