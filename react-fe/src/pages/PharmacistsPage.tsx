import { useQuery } from '@tanstack/react-query';
import { Pharmacist } from '../@types';
import { ApiException, Fetcher } from '../lib/fetcher';
import DataWidget from '../components/shared/DataWidget';
import { toastMessage } from '../components/shared/toast';

const PharmacistsPage = () => {
  const { isLoading, data: pharmacistsData, error, refetch } = useQuery<any, ApiException, { data: Pharmacist[] }>({
    queryKey: ["pharmacists"],
    queryFn: () => Fetcher.get("/pharmacists"),
    retry: false
  });

  return (
    <div className="bg-white p-6">
      <DataWidget isLoading={isLoading} error={error} retry={refetch}>
        {pharmacistsData && <div>
          <h1 className="font-semibold">Pharmacists retrieved successfully!</h1>
          <table className="w-full text-left mt-2">
            <thead>
              <tr>
                {
                  ["No", "Name", "Phone Number", "Gender", "Age", "Role", "Action"].map((item) => (<th key={item} className={`font-normal text-gray-600 py-3`}>{item}</th>))
                }
              </tr>
            </thead>
            <tbody>
              {pharmacistsData.data?.map((pharmacist, index) => {
                return <tr key={index}>
                  <td>{index + 1}</td>
                  <td>
                    <p className="my-2">{pharmacist.name}</p>
                  </td>
                  <td>{pharmacist.phoneNumber}</td>
                  <td>{pharmacist.gender}</td>
                  <td>{pharmacist.age}</td>
                  <td>{pharmacist.role}</td>
                  <td>
                    <button className="bg-primary text-white text-xs p-2 px-4 my-2" onClick={async () => {
                      try {
                        const result = await Fetcher.post("/patients/select-pharmacist?phoneNumber=" + pharmacist.phoneNumber, {});
                        toastMessage(result.message, 'info');
                      } catch (error: any) {
                        toastMessage(error.message);
                      }
                    }}>SELECT</button>
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

export default PharmacistsPage;