import { BrowserRouter, Routes, Route } from "react-router-dom";
import ShortenPage from "./pages/shortnerPage";
import LoginPage from "./pages/LoginPage";
import AnalyticsPage from "./pages/AnalyticPage";
import DashboardPage from "./pages/DashboardPage";
import ProtectedRoute from "./ProtectedRoute";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<ShortenPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <DashboardPage />
            </ProtectedRoute>
          }
        />
        <Route
          path="/analytics/:shortCode"
          element={
            <ProtectedRoute>
              <AnalyticsPage />
            </ProtectedRoute>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
