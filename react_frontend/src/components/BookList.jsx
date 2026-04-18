import React, { useState } from 'react';
import { Link } from "react-router-dom";

export default function BookList({ books, onAddBook, onDeleteBook }) {
  const [id, setId] = useState('');
  const [title, setTitle] = useState('');
  const [price, setPrice] = useState('');
  const [author, setAuthor] = useState('');
  const [isbn, setIsbn] = useState('');
  const [releaseDate, setReleaseDate] = useState('');
  const [publisher, setPublisher] = useState('');
  const [weight, setWeight] = useState('');
  const [height, setHeight] = useState('');
  const [width, setWidth] = useState('');
  const [depth, setDepth] = useState('');

  async function handleSubmit(e) {
    e.preventDefault();

    await onAddBook({
      id: Number(id),
      title,
      price: parseFloat(price),
      author,
      isbn,
      releaseDate,
      publisher,
      weight: parseFloat(weight),
      height: parseFloat(height),
      width: parseFloat(width),
      depth: parseFloat(depth)
    });

    setId('');
    setTitle('');
    setPrice('');
    setAuthor('');
    setIsbn('');
    setReleaseDate('');
    setPublisher('');
    setWeight('');
    setHeight('');
    setWidth('');
    setDepth('');
  }

  return (
    <div className="card">
      <div className="section-title">Books</div>

      <form onSubmit={handleSubmit} className="form-column">
        <input placeholder="ID" type="number" value={id} onChange={e => setId(e.target.value)} required />
        <input placeholder="Title" value={title} onChange={e => setTitle(e.target.value)} required />
        <input placeholder="Author" value={author} onChange={e => setAuthor(e.target.value)} required />
        <input placeholder="Price" type="number" value={price} onChange={e => setPrice(e.target.value)} required />
        <input placeholder="ISBN" value={isbn} onChange={e => setIsbn(e.target.value)} required />
        <input placeholder="Publisher" value={publisher} onChange={e => setPublisher(e.target.value)} required />
        <input type="date" value={releaseDate} onChange={e => setReleaseDate(e.target.value)} />

        <input placeholder="Weight" type="number" value={weight} onChange={e => setWeight(e.target.value)} />
        <input placeholder="Height" type="number" value={height} onChange={e => setHeight(e.target.value)} />
        <input placeholder="Width" type="number" value={width} onChange={e => setWidth(e.target.value)} />
        <input placeholder="Depth" type="number" value={depth} onChange={e => setDepth(e.target.value)} />

        <button className="btn">Add Book</button>
      </form>

      <ul className="list">
        {books.map(b => (
          <li key={b.id} className="list-item">
            <strong>{b.title}</strong>
            <div>{b.author}</div>
            <div>{b.publisher}</div>
            <div>{b.releaseDate}</div>

            <button className="btn small red" onClick={() => onDeleteBook(b.id)}>Delete</button>
            <Link style={{ marginLeft: "10px" }} className="btn" to={`/edit-book/${b.id}`}>Edit</Link>
          </li>
        ))}
      </ul>
    </div>
  );
}
