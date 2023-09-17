import { Link } from "react-router-dom"
import { SideBarITem } from "../../@types"
import DashboardIcon from "./icons/DashboardIcon"
import ProductsIcon from "./icons/ProductsIcon"
import UsersIcon from "./icons/UsersIcon"
import AnalyticsIcon from "./icons/AnalyticsIcon"
import ProfileIcon from "./icons/ProfileIcon"
import SidebarMenuItem from "./SidebarMenuItem"

const sidebarItems: SideBarITem[] = [
    {
        icon: DashboardIcon,
        title: 'Dashboard',
        url: '',
    },
    {
        icon: ProductsIcon,
        title: 'Patients',
        url: '/patients',
    },
    {
        icon: AnalyticsIcon,
        title: 'Physicians',
        url: '/physicians',
    },
    {
        icon: UsersIcon,
        title: 'Pharmacists',
        url: '/pharmacists',
    },
    {
        icon: ProfileIcon,
        title: 'Consultation',
        url: '/consultation',
    },
    {
        icon: ProductsIcon,
        title: 'Medicines',
        url: '/medicines',
    },
];

const DashboardSidebar = () => {
    return (
        <>
            <div>
                <Link to='/dashboard' className="flex gap-2 items-center">
                    <img src="/logo.png" alt="My Logo" height={40} width={40} className="" />
                    <h1 className="text-xl font-extrabold text-primary">E-Hospital</h1>
                </Link>
            </div>
            <div className="flex flex-col mt-16 gap-3">
                {
                    sidebarItems.filter((item) => {
                        const currentUserRole = localStorage.getItem("role");
                        if (currentUserRole == null) return false;
                        if (currentUserRole === "PATIENT" && item.url === '/patients') return false;
                        if (currentUserRole === "PHARMACIST" && (item.url === '/pharmacists' || item.url === '/physicians' || item.url === '/consultation')) return false;
                        if (currentUserRole === "PHYSICIAN" && (item.url === '/physicians' || item.url === '/pharmacists' || item.url === '/medicines' || item.url === '/consultation')) return false;
                        return true;
                    }).map((item, index) => (<SidebarMenuItem item={item} key={index} />))
                }
            </div>
        </>
    )
}

export default DashboardSidebar