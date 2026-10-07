import { useState } from "react";
import { shortenUrl } from "../api/client";
import "./Shortner.css";

function UrlPage() {
  const [url, setUrl] = useState("");
  const [shortUrl, setShortUrl] = useState(null);
  const [error, setError] = useState(null);
  const [submitting, setSubmitting] = useState(false);
  const [copied, setCopied] = useState(false);

 const BASE_URL = import.meta.env.VITE_API_URL;

const handleSubmit = async (e) => {
  e.preventDefault();
  setError(null);
  setShortUrl(null);
  setSubmitting(true);

  try {
    const data = await shortenUrl(url);
    setShortUrl(`${BASE_URL}/${data.shortCode}`);
  } catch (err) {
    setError(err.message);
  } finally {
    setSubmitting(false);
  }
};


  const handleCopy = async () => {
    try {
      await navigator.clipboard.writeText(shortUrl);
      setCopied(true);
      setTimeout(() => setCopied(false), 2000);
    } catch (error) {
      console.error("Failed to copy:", error);
    }
  };

  return (
    <div className="url-page">
      {/* Navbar */}
      <nav className="navbar">
        <div className="navbar-logo">
          <div className="logo-icon">↗</div>
          <span>Shortly</span>
        </div>

        <div className="admin-panel">
          <div className="admin-avatar">A</div>
          <div className="admin-info">
            <span className="admin-name">Admin</span>
            <span className="admin-role">Administrator</span>
          </div>
          <a className="settings-button" href="/login" title="Admin login">
            ⚙
          </a>
        </div>
      </nav>

      {/* Main Content */}
      <main className="url-main">
        <div className="hero-content">
          <div className="hero-badge">
            <span></span>
            URL SHORTENER
          </div>

          <h1>
            Make your links
            <br />
            <span>short &amp; simple.</span>
          </h1>

          <p>
            Transform long URLs into short, memorable links that are easy to
            share and track.
          </p>

          {/* URL Input */}
          <form className="url-box" onSubmit={handleSubmit}>
            <div className="url-input-wrapper">
              <span className="link-icon">🔗</span>
              <input
                type="url"
                placeholder="Paste your long URL here..."
                value={url}
                onChange={(e) => setUrl(e.target.value)}
                required
              />
            </div>

            <button className="shorten-button" type="submit" disabled={submitting}>
              {submitting ? "Shortening…" : "Shorten URL"}
              <span>→</span>
            </button>
          </form>

          {error && <p className="url-error">{error}</p>}

          {/* Result */}
          {shortUrl && (
            <div className="result-box">
              <div className="result-left">
                <div className="result-icon">✓</div>
                <div>
                  <span className="result-label">YOUR SHORT URL</span>
                  <strong>{shortUrl}</strong>
                </div>
              </div>

              <button className="copy-button" onClick={handleCopy}>
                {copied ? "Copied!" : "Copy"}
              </button>
            </div>
          )}

          {/* Features */}
          <div className="features">
            <div className="feature">
              <div className="feature-icon">⚡</div>
              <div>
                <strong>Instant</strong>
                <span>Create links in seconds</span>
              </div>
            </div>

            <div className="feature">
              <div className="feature-icon">🔒</div>
              <div>
                <strong>Secure</strong>
                <span>Protected admin access</span>
              </div>
            </div>

            <div className="feature">
              <div className="feature-icon">📊</div>
              <div>
                <strong>Analytics</strong>
                <span>Track your link performance</span>
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}

export default UrlPage;
