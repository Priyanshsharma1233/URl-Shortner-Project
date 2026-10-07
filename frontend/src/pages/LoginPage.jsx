import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../api/client";
import "./login.css"

function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(null);
  const [submitting, setSubmitting] = useState(false);

  const navigate = useNavigate();

  async function handleSubmit(e) {
    e.preventDefault();
    setError(null);
    setSubmitting(true);

    try {
      const data = await login(username, password);
      localStorage.setItem("token", data.token);
      navigate("/dashboard", { replace: true });
    } catch (err) {
      setError(err.message);
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <div className="login-page">
      <form className="login-card" onSubmit={handleSubmit}>
        <h1>Admin login</h1>
        <p className="login-sub">Sign in to view link analytics.</p>

        <label className="login-label">
          Username
          <input
            className="login-input"
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            autoFocus
          />
        </label>

        <label className="login-label">
          Password
          <input
            className="login-input"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </label>

        {error && <p className="login-error">{error}</p>}

        <button className="login-btn" type="submit" disabled={submitting}>
          {submitting ? "Signing in…" : "Sign in"}
        </button>
      </form>
    </div>
  );
}

export default LoginPage;