import { useQuery, } from '@tanstack/react-query';
import { ApiException, Fetcher } from '../lib/fetcher';
import DataWidget from '../components/shared/DataWidget';
import { useState } from 'react';
import { toastMessage } from '../components/shared/toast';
import { Modal } from 'antd';
import { Formik } from 'formik';
import * as Yup from "yup";
import TextInput from '../components/auth/TextInput';
import { Medicine } from '../@types';
import { CSVLink } from "react-csv";

const MedicinesPage = () => {
    const isPharmacist = localStorage.getItem("role") === "PHARMACIST";

    const { isLoading, data: medicinesData, error, refetch } = useQuery<any, ApiException, { data: Medicine[] }>({
        queryKey: ["medicines"],
        queryFn: () => Fetcher.get(isPharmacist ? "/pharmacists/medicines" : "/patients/medicines/findByPatient"),
        retry: false,
    });

    const [isModalOpen, setIsModalOpen] = useState(false);

    const showModal = () => {
        setIsModalOpen(true);
    };

    const handleOk = () => {
        setIsModalOpen(false);
    };

    const handleCancel = (hasResult: boolean = false) => {
        setIsModalOpen(false);
        if (hasResult) {
            toastMessage("New medicine has added successfully!", 'info');
            refetch();
        }
    }

    return (
        <div className="bg-white p-6">
            <DataWidget isLoading={isLoading} error={error} retry={refetch}>
                {medicinesData && <div>
                    <div className='flex justify-between'>
                        <h1 className="font-semibold">Medicines retrieved successfully!</h1>
                        {isPharmacist ? <button className='bg-primary text-white px-5 py-2 text-xs font-semibold' onClick={showModal}>Add Medicine</button> :
                            <>
                                {medicinesData?.data?.length > 0 && <CSVLink data={medicinesData.data} filename='medicines.csv' className='bg-primary text-white px-5 py-2 text-xs font-semibold'>
                                    Download CSV
                                </CSVLink>}
                            </>
                        }
                    </div>

                    <table className="w-full text-left mt-2">
                        <thead>
                            <tr>
                                {
                                    ["No", "Name", "Prize", "Expiration"].map((item) => (<th key={item} className={`font-normal text-gray-600 py-3`}>{item}</th>))
                                }
                            </tr>
                        </thead>
                        <tbody>
                            {medicinesData.data?.map((medicine, index) => {
                                return <tr key={index}>
                                    <td>{index + 1}</td>
                                    <td>
                                        <p className="my-2">{medicine.medName}</p>
                                    </td>
                                    <td>{medicine.medPrice}</td>
                                    <td>{medicine.medExpiration}</td>
                                </tr>
                            })}
                        </tbody>
                    </table>
                    <Modal
                        title={<p className="text-lg">Add medicine</p>}
                        open={isModalOpen}
                        onOk={handleOk}
                        onCancel={() => handleCancel()}
                        footer={false}
                        bodyStyle={{ padding: '2px' }}
                    >

                        <Formik
                            initialValues={{ medName: '', medPrice: '', medExpiration: '', submit: null }}
                            validationSchema={Yup.object().shape({
                                medName: Yup.string().max(50).required('Medicine name is required'),
                                medPrice: Yup.string().max(50).required('Medicine price is required'),
                                medExpiration: Yup.string().max(50).required('Medicine expiration is required'),
                            })}
                            onSubmit={async (values, { setErrors, setStatus, setSubmitting, setValues, setTouched }) => {
                                try {
                                    await Fetcher.post('pharmacists/add-medicine', {
                                        ...values,
                                    });

                                    handleCancel(true);
                                    setValues({ medName: '', medPrice: '', medExpiration: '', submit: null });
                                    setSubmitting(false);
                                    setStatus({ success: true });
                                    setTouched({});
                                } catch (err: any) {
                                    setStatus({ success: false });
                                    setErrors({ submit: err.message || "Something went wrong!" });
                                    setSubmitting(false);
                                }
                            }}
                        >
                            {({ errors, handleBlur, handleChange, handleSubmit, isSubmitting, touched, values }) => {
                                return <form noValidate onSubmit={handleSubmit} className="flex flex-col gap-3 my-4">
                                    {errors.submit && <p className="bg-red-500 p-2 px-4 text-white text-sm text-center" dangerouslySetInnerHTML={{ __html: errors.submit }}></p>}
                                    <TextInput
                                        type="text"
                                        name="medName"
                                        label="Medicine name"
                                        placeholder="Enter medicine name"
                                        error={errors.medName}
                                        value={values.medName}
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        isTouched={Boolean(touched.medName)}
                                        className="outline-none p-3 px-4 bg-primary bg-opacity-10 w-full mb-0.5"
                                    />
                                    <TextInput
                                        type="text"
                                        name="medPrice"
                                        label="Medicine price"
                                        placeholder="Enter medicine price"
                                        error={errors.medPrice}
                                        value={values.medPrice}
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        isTouched={Boolean(touched.medPrice)}
                                        className="outline-none p-3 px-4 bg-primary bg-opacity-10 w-full mb-0.5"
                                    />
                                    <TextInput
                                        type="text"
                                        name="medExpiration"
                                        label="Medicine expiration"
                                        placeholder="Enter medicine expiration"
                                        error={errors.medExpiration}
                                        value={values.medExpiration}
                                        onChange={handleChange}
                                        onBlur={handleBlur}
                                        isTouched={Boolean(touched.medExpiration)}
                                        className="outline-none p-3 px-4 bg-primary bg-opacity-10 w-full mb-0.5"
                                    />

                                    <div className="flex justify-end pt-2">
                                        <button type="submit" className="bg-primary disabled:bg-gray-400 text-white p-2.5 px-6 flex items-center gap-2 hover:bg-opacity-80" disabled={isSubmitting}>
                                            <span>
                                                {isSubmitting ? "Please wait..." : "Add Medicine"}
                                            </span>
                                        </button>
                                    </div>
                                </form>
                            }}
                        </Formik>

                    </Modal>
                </div>}
            </DataWidget>

        </div>
    );
}

export default MedicinesPage