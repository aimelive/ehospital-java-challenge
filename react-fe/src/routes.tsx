import { Route, Routes } from "react-router-dom"
import HomePage from "./pages/HomePage"
import NotFoundPage from "./pages/NotFound";
import DashboardLayout from "./components/dashboard/DashboardLayout";
import DashboardPage from "./pages/dashboard/DashboardPage";
import PatientsPage from "./pages/PatientsPage";
import PhysiciansPage from "./pages/PhysiciansPage";
import PharmacistsPage from "./pages/PharmacistsPage";
import ConsultationsPage from "./pages/ConsultationsPage";
import MedicinesPage from "./pages/MedicinesPage";

const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/dashboard" element={<DashboardLayout />}>
                <Route path="/dashboard" element={<DashboardPage />} />
                <Route path="/dashboard/patients" element={<PatientsPage />} />
                <Route path="/dashboard/physicians" element={<PhysiciansPage />} />
                <Route path="/dashboard/pharmacists" element={<PharmacistsPage />} />
                <Route path="/dashboard/consultation" element={<ConsultationsPage />} />
                <Route path="/dashboard/medicines" element={<MedicinesPage />} />
                <Route path="*" element={<NotFoundPage />} />
            </Route>
            <Route path="*" element={<NotFoundPage />} />
        </Routes>
    );
}

export default AppRoutes