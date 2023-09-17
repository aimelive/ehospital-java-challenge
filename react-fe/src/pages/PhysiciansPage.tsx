import { useQuery } from "@tanstack/react-query";
import { ApiException, Fetcher } from "../lib/fetcher";
import { Physician } from "../@types";
import DataWidget from "../components/shared/DataWidget";
import { toastMessage } from "../components/shared/toast";


const PhysiciansPage = () => {
    const { isLoading, data: physiciansData, error, refetch } = useQuery<any, ApiException, { data: Physician[] }>({
        queryKey: ["physicians"],
        queryFn: () => Fetcher.get("/physicians"),
        retry: false
    });

    return (
        <div className="bg-white p-6">
            <DataWidget isLoading={isLoading} error={error} retry={refetch}>
                {physiciansData && <div>
                    <h1 className="font-semibold">Physicians retrieved successfully!</h1>
                    <table className="w-full text-left mt-2">
                        <thead>
                            <tr>
                                {
                                    ["No", "Name", "Email", "Gender", "Age", "Role", "Action"].map((item) => (<th key={item} className={`font-normal text-gray-600 py-3`}>{item}</th>))
                                }
                            </tr>
                        </thead>
                        <tbody>
                            {physiciansData.data?.map((physician, index) => {
                                return <tr key={index}>
                                    <td>{index + 1}</td>
                                    <td>
                                        <p className="my-2">{physician.name}</p>
                                    </td>
                                    <td>{physician.email}</td>
                                    <td>{physician.gender}</td>
                                    <td>{physician.age}</td>
                                    <td>{physician.role}</td>
                                    <td>
                                        <button className="bg-primary text-white text-xs p-2 px-4 my-2" onClick={async () => {
                                            try {
                                                const result = await Fetcher.post("/patients/select-physician?email=" + physician.email, {});
                                                toastMessage(result.message, 'info');
                                            } catch (error: any) {
                                                toastMessage(error.message);
                                            }
                                        }}>
                                            {localStorage.getItem("role") === "PATIENT" ? "SELECT" : "CONSULT"}
                                        </button>
                                    </td>
                                </tr>
                            })}
                        </tbody>
                    </table>
                </div>}
            </DataWidget>
        </div>
    );
}

export default PhysiciansPage;