import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getBooks, createBook, deleteBook } from "../services/api";
import BookList from "../components/BookList";

export default function BooksPage() {
  const [books, setBooks] = useState([]);

  useEffect(() => {
    loadBooks();
  }, []);

  async function loadBooks() {
    const data = await getBooks();
    setBooks(data);
  }

  async function handleAddBook(book) {
    const newBook = await createBook(book);
    setBooks(prev => [...prev, newBook]);
  }

  async function handleDeleteBook(id) {
    await deleteBook(id);
    setBooks(prev => prev.filter(b => b.id !== id));
  }

  return (
    <div className="layout">

      <BookList
        books={books}
        onAddBook={handleAddBook}
        onDeleteBook={handleDeleteBook}
      />
      <div style={{ margin: "50px", justifyContent: "center", display: "flex", textAlign: "center" }}>
        <Link to="/" id="btn-2" className="btn-2">Volver a Home</Link>
      </div>
    </div>
  );
}
