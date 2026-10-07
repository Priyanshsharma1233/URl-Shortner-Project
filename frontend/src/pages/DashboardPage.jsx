import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Dashboard.css";

function DashboardPage() {
  const [code, setCode] = useState("");
  const navigate = useNavigate();
function handleView(e) {
  e.preventDefault();
  const trimmed = code.trim();
  if (!trimmed) {
    return;
  }

  const extracted = trimmed.includes("/") ? trimmed.split("/").pop() : trimmed;
  navigate(`/analytics/${extracted}`);
}

  function handleLogout() {
    localStorage.removeItem("token");
    navigate("/login", { replace: true });
  }

  return (
    <div className="db-page">
      <header className="db-topbar">
        <span className="db-title">Admin dashboard</span>
        <button className="db-logout" onClick={handleLogout}>
          Log out
        </button>
      </header>

      <main className="db-content">
        <div className="db-card">
          <h1>View link analytics</h1>
          <p className="db-sub">
            Enter a short code to see its click stats and daily chart.
          </p>

          <form className="db-form" onSubmit={handleView}>
            <input
              className="db-input"
              type="text"
              placeholder="e.g. zTPELELA"
              value={code}
              onChange={(e) => setCode(e.target.value)}
              autoFocus
            />
            <button className="db-btn" type="submit">
              View analytics
            </button>
          </form>
        </div>

        <div className="db-note">
          Tip: the short code is the part after your domain in a shortened
          link — for <code>http://localhost:8080/zTPELELA</code> that's{" "}
          <code>zTPELELA</code>.
        </div>
      </main>
    </div>
  );
}

export default DashboardPage;
