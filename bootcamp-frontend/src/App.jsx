import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import './App.css'
import Login from './pages/auth/Login'
import Register from './pages/auth/Register'
import Home from './pages/home/Home'
import EditUser from './pages/user/EditUser'
import OtpEmail from './pages/auth/OtpEmail'
import Otp from './pages/auth/Otp'
import ResetPassword from './pages/auth/ResetPassword'
import Profile from './pages/profile/Profile'
import MyNotes from './pages/myNotes/MyNotes'
import SharedNotes from './pages/sharedNotes/SharedNotes'
import PublishedNotes from './pages/publishedNotes/PublishedNotes'

function App() {

  const router = createBrowserRouter([
    {
      path:'/',
      element: <Login/>
    },
    {
      path: '/login',
      element: <Login />
    }, 
    {
      path: '/register',
      element: <Register />
    },
    {
      path: '/home',
      element: <Home />
    },
    {
      path: '/forgotPassword',
      element: <OtpEmail />
    },
    {
      path: "/otp",
      element: <Otp />
    },
    {
      path: '/resetPassword',
      element: <ResetPassword />
    },{
      path: '/profile',
      element: <Profile/>
    },
    {
      path:'/notes',
      element:<MyNotes/>
    },
    {
      path:'/sharedNotes',
      element:<SharedNotes/>
    },
    {
      path:'/publishedNotes',
      element:<PublishedNotes/>
    }
  ])

  return (
    <>
      <RouterProvider router={router} />
    </>
  )
}

export default App
