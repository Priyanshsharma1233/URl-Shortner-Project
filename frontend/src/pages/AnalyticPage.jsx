import { useEffect, useState } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
  CartesianGrid,
} from "recharts";
import { getAnalytics } from "../api/client";
import "./Analytic.css";

function AnalyticsPage() {
  const { shortCode } = useParams();
  const navigate = useNavigate();

  const [data, setData] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let cancelled = false;

    async function load() {
      setLoading(true);
      setError(null);
      try {
        const result = await getAnalytics(shortCode);
        if (!cancelled) setData(result);
      } catch (err) {
        if (cancelled) return;
        if (err.message === "UNAUTHORIZED") {
          localStorage.removeItem("token");
          navigate("/login", { replace: true });
          return;
        }
        setError(err.message);
      } finally {
        if (!cancelled) setLoading(false);
      }
    }

    load();
    return () => {
      cancelled = true;
    };
  }, [shortCode, navigate]);

  function handleLogout() {
    localStorage.removeItem("token");
    navigate("/login", { replace: true });
  }

  if (loading) {
    return <div className="an-state">Loading analytics…</div>;
  }

  if (error) {
    return (
      <div className="an-state an-state-error">
        Couldn't load analytics: {error}
      </div>
    );
  }

  const last7 = data.clicksByDay.slice(-7);

  return (
    <div className="an-page">
      <div className="an-topbar">
        <Link to="/dashboard" className="an-back">
          ← Dashboard
        </Link>
        <button className="an-logout" onClick={handleLogout}>
          Log out
        </button>
      </div>

      <div className="an-header">
        <h1>Analytics</h1>
        <code className="an-code">/{data.shortCode}</code>
      </div>

      <section className="an-stats">
        <div className="an-card">
          <span className="an-card-label">Total clicks</span>
          <span className="an-card-value">{data.totalClicks}</span>
        </div>
        <div className="an-card">
          <span className="an-card-label">Today</span>
          <span className="an-card-value">
            {data.clicksByDay[data.clicksByDay.length - 1]?.count ?? 0}
          </span>
        </div>
        <div className="an-card">
          <span className="an-card-label">Last 7 days</span>
          <span className="an-card-value">
            {last7.reduce((sum, d) => sum + d.count, 0)}
          </span>
        </div>
      </section>

      <section className="an-chart-section">
        <h2>Clicks, last 30 days</h2>
        {data.totalClicks === 0 ? (
          <div className="an-empty">No clicks yet on this link.</div>
        ) : (
          <div className="an-chart-wrap">
            <ResponsiveContainer width="100%" height={280}>
              <BarChart data={data.clicksByDay}>
                <CartesianGrid strokeDasharray="3 3" vertical={false} />
                <XAxis
                  dataKey="date"
                  tickFormatter={(d) => d.slice(5)}
                  interval={4}
                  fontSize={12}
                />
                <YAxis allowDecimals={false} fontSize={12} />
                <Tooltip />
                <Bar dataKey="count" fill="#6d5bf0" radius={[4, 4, 0, 0]} />
              </BarChart>
            </ResponsiveContainer>
          </div>
        )}
      </section>
    </div>
  );
}

export default AnalyticsPage;
