import { useQuery } from "@tanstack/react-query";
import { ApiException, Fetcher } from "../lib/fetcher";
import DataWidget from "../components/shared/DataWidget";
import { Patient } from "../@types";
import PatientListItem from "../components/dashboard/PatientListItem";


const PatientsPage = () => {
    const { isLoading, data: patientsData, error, refetch } = useQuery<any, ApiException, { data: Patient[] }>({
        queryKey: ["patients"],
        queryFn: () => Fetcher.get(`/patients/findBy${localStorage.getItem("role")}`),
        retry: false
    });

    return (
        <div className="bg-white p-6">
            <DataWidget isLoading={isLoading} error={error} retry={refetch}>
                {patientsData && <div>
                    <h1 className="font-semibold">Patients retrieved successfully!</h1>
                    <table className="w-full text-left mt-2">
                        <thead>
                            <tr>
                                {
                                    ["No", "Name", "Username", "Gender", "Age", "Role", "Action"].map((item) => (<th key={item} className={`font-normal text-gray-600 py-3`}>{item}</th>))
                                }
                            </tr>
                        </thead>
                        <tbody>
                            {patientsData.data?.map((patient, index) => {
                                return <PatientListItem key={index} index={index} patient={patient} />
                            })}
                        </tbody>
                    </table>
                </div>
                }
            </DataWidget>
        </div>
    );
}

export default PatientsPage