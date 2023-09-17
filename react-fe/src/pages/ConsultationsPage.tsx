import { ApiException, Fetcher } from '../lib/fetcher';
import DataWidget from '../components/shared/DataWidget';
import { useQuery } from '@tanstack/react-query';
import { Consultation } from '../@types';

const ConsultationsPage = () => {

    const { isLoading, data: consultationsData, error, refetch } = useQuery<any, ApiException, { data: Consultation }>({
        queryKey: ["consultations"],
        queryFn: () => Fetcher.get("/patients/consultation"),
        retry: false
    });

    return (
        <div className="bg-white p-6">
            <DataWidget isLoading={isLoading} error={error} retry={refetch}>
                {consultationsData?.data ? <div>
                    <h1 className="font-semibold text-lg">Patient consultation retrieved successfully!</h1>
                    <div className='my-2 flex flex-col gap-1'>
                        <div className='flex gap-2'>
                            <p className='font-semibold'>Disease:</p>
                            <p>{consultationsData.data.disease}</p>
                        </div>
                        <div className='flex gap-2'>
                            <p className='font-semibold'>Description:</p>
                            <p>{consultationsData.data.description}</p>
                        </div>
                        <div className='flex gap-2'>
                            <p className='font-semibold'>Physician:</p>
                            <p>{consultationsData.data.physician.name}({consultationsData.data.physician.email})</p>
                        </div>
                    </div>
                </div> : <h1 className="font-semibold text-lg">No consultation has made for you!</h1>}
            </DataWidget>
        </div>
    );
}

export default ConsultationsPage