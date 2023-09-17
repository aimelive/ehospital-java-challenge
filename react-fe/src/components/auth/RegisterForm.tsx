import * as Yup from 'yup';
import { Formik } from 'formik';
import { Fetcher } from '../../lib/fetcher';
import TextInput from './TextInput';
import { Constants } from '../../lib/constants';
import SelectInput from './SelectInput';
import { toastMessage } from '../shared/toast';

interface Props {
    onSwitch: () => void;
}

const RegisterForm = ({ onSwitch }: Props) => {
    return (
        <div className="">
            <div className="bg-primary-light p-6 rounded-3xl text-center flex flex-col gap-2">
                <h1 className="text-primary font-bold text-3xl">Register !</h1>
                <p className="text-grey">Create new account</p>
            </div>
            <Formik
                initialValues={{ name: '', username: '', email: '', phoneNumber: '', password: '', gender: 'Male', role: Constants.ROLES[0], age: 0, submit: null }}
                validationSchema={Yup.object().shape({
                    name: Yup.string().max(50).required('Full name is required'),
                    username: Yup.string().max(50).optional(),
                    email: Yup.string().email('Invalid email').optional(),
                    phoneNumber: Yup.string()
                        .matches(/^\d{10}$/, 'Phone number must be 10 digits')
                        .optional(),
                    password: Yup.string().min(4).max(15).required('Password is required'),
                    gender: Yup.string().oneOf(['Male', 'Female'], 'Invalid gender').required('Gender is required'),
                    role: Yup.string().oneOf(Constants.ROLES, 'Invalid role').required('Role is required'),
                    age: Yup.number().integer().min(18, 'Age must be at least 18').required('Age is required'),
                })}
                onSubmit={async (values, { setErrors, setStatus, setSubmitting }) => {
                    try {
                        const result = await Fetcher.post("auth/register", { ...values });
                        toastMessage(`${result.data.role} account created successfully! Please login to continue.`, 'info');
                        onSwitch();
                        setSubmitting(false);
                        setStatus({ success: true });
                    } catch (err: any) {
                        setStatus({ success: false });
                        setErrors({ submit: err.message || "Something went wrong abasa" });
                        setSubmitting(false);
                    }
                }}
            >
                {({ errors, handleBlur, handleChange, handleSubmit, isSubmitting, touched, values }) => {
                    return <form className="p-12 flex gap-5 flex-col" noValidate onSubmit={handleSubmit}>
                        {errors.submit && <p className="bg-red-500 p-2 px-4 text-white text-sm rounded-xl text-center" dangerouslySetInnerHTML={{ __html: errors.submit }}></p>}
                        <div className='grid grid-cols-2 gap-4'>
                            <TextInput
                                name="name"
                                label="Full name"
                                placeholder="Enter your full name"
                                error={errors.name}
                                value={values.name}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                isTouched={Boolean(touched.name)}
                            />
                            <SelectInput
                                items={['Male', 'Female']}
                                name="gender"
                                label="Gender"
                                placeholder="Choose your gender"
                                error={errors.gender}
                                value={values.gender}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                isTouched={Boolean(touched.gender)}
                            />
                        </div>
                        <div className='grid grid-cols-2 gap-4'>

                            <SelectInput
                                items={Constants.ROLES}
                                name="role"
                                label="Role"
                                placeholder="Choose your role"
                                error={errors.role}
                                value={values.role}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                isTouched={Boolean(touched.role)}
                            />
                            <TextInput
                                name="age"
                                label="Age"
                                placeholder="Enter your age"
                                error={errors.age}
                                value={values.age}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                isTouched={Boolean(touched.age)}
                            />

                        </div>
                        {values.role === Constants.ROLES[1] && <TextInput
                            name="email"
                            label="Email"
                            placeholder="Enter Your email"
                            error={errors.email}
                            value={values.email}
                            onChange={handleChange}
                            onBlur={handleBlur}
                            isTouched={Boolean(touched.email)}
                        />}
                        {values.role === Constants.ROLES[2] && <TextInput
                            name="phoneNumber"
                            label="Phone number"
                            placeholder="Enter Your phoneNumber"
                            error={errors.phoneNumber}
                            value={values.phoneNumber}
                            onChange={handleChange}
                            onBlur={handleBlur}
                            isTouched={Boolean(touched.phoneNumber)}
                        />}
                        {values.role === Constants.ROLES[0] && <TextInput
                            name="username"
                            label="Username"
                            placeholder="Enter Your username"
                            error={errors.username}
                            value={values.username}
                            onChange={handleChange}
                            onBlur={handleBlur}
                            isTouched={Boolean(touched.username)}
                        />}
                        <TextInput
                            name="password"
                            label="Password"
                            placeholder="Enter Your Password"
                            error={errors.password}
                            value={values.password}
                            onChange={handleChange}
                            onBlur={handleBlur}
                            isTouched={Boolean(touched.password)}
                        />
                        <button type="submit" className="bg-primary disabled:bg-gray-400 text-white p-3 rounded-2xl w-full" disabled={isSubmitting}>{isSubmitting ? "Please wait..." : "Register"}</button>
                    </form>
                }}
            </Formik>

            <div className="flex justify-center gap-2 text-sm">
                <span>Already have an account?</span> <button className="text-primary hover:underline font-semibold" onClick={onSwitch}>Go back to login</button>
            </div>
        </div>
    )
}

export default RegisterForm