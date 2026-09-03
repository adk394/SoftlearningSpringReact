import { Link } from "react-router-dom";

export default function HomePage() {
  return (
    <main className="home layout">
      <section className="welcome-panel">
        <div className="eyebrow"><span className="pulse" /> Bookora workspace</div>
        <h1>Everything your<br /><em>bookstore</em> needs.</h1>
        <p className="lead">A quiet space to keep your catalogue, clients and orders moving.</p>
        <Link className="primary-action" to="/orders">Open orders <span>↗</span></Link>
        <div className="welcome-orbit" aria-hidden="true">
          <span className="orbit-card orbit-card-one">01<br /><small>catalogue</small></span>
          <span className="orbit-card orbit-card-two">02<br /><small>delivery</small></span>
          <span className="orbit-card orbit-card-three">03<br /><small>people</small></span>
          <div className="orbit-core">B</div>
        </div>
      </section>
      <section className="home-section">
        <div className="section-heading"><div><span className="eyebrow">Your library</span><h2>Manage the essentials</h2></div><span className="section-count">03 modules</span></div>
        <div className="module-grid">
          <Link className="module-card module-books" to="/books"><span className="module-number">01</span><span className="module-icon">✦</span><h3>Books</h3><p>Keep your collection in order.</p><span className="module-arrow">↗</span></Link>
          <Link className="module-card module-clients" to="/clients"><span className="module-number">02</span><span className="module-icon">◌</span><h3>Clients</h3><p>Know the readers behind every order.</p><span className="module-arrow">↗</span></Link>
          <Link className="module-card module-orders" to="/orders"><span className="module-number">03</span><span className="module-icon">→</span><h3>Orders</h3><p>Follow each delivery from start to finish.</p><span className="module-arrow">↗</span></Link>
        </div>
      </section>
    </main>
  );
}
